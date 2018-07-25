# Light DI for Java

Light dependency injection library for Java based on Reflection.

## Features:
- Annotation configuration
- Constructor and setter arguments injection
- Values injection from property file `application.properties` in `key=value` format

## Annotations:
- `@Bean` and `@Bean(name = "beanName")` for singletons on class
- `@Inject` for injecting `@Bean` (setter injection)
- `@Value` for injecting properties:
    - String
    - Integer
    - Double
    - Boolean
- `@Configuration` and nested `@Bean`'s for defining configuration based on methods

## Limitations:
- Only singletons
- Class that annotated with `@Bean` should have either public default constructor or public constructor with arguments, 
where all arguments should be references on other declared `@Bean`'s

## Usage:

### Singleton with default constructor and setter injection 
```java
@Bean
public class Airplane {
    @Inject
    private Fuselage fuselage;
    
    @Inject(name = "left")
    private Engine leftEngine;
    
    @Inject(name = "right")
    private Engine rightEngine;
}
```

### Singleton with constructor and setter injection
```java
@Bean(name = "monoplane")
public class Airplane {
    private Fuselage fuselage;
    
    public Airplane(Fuselage fuselage) {
        this.fuselage = fuselage;
    }
    
    @Inject(name = "left")
    private Engine leftEngine;
    
    @Inject(name = "right")
    private Engine rightEngine;
}
```

### Values injection
```java
@Bean(name = "biplane")
public class Airplane {
    @Value("name")
    private String name;
    
    @Value("hp")
    private Double hp;
    
    @Value("flights")
    private Integer flightsCount;
    
    @Value("ready")
    private Boolean isReady;
}
```

### Configuration
```java
@Configuration
public class PlaneConfiguration {
    @Bean
    public Airplane airplane() {
        Airplane airplane = new Airplane(new Fuselage());
        airplane.setLeftEngine(new Engine());
        airplane.setRightEngine(new Engine());
        return airplane;
    }

    @Bean(name = "monoplane")
    public Airplane monoplane() {
        Airplane airplane = new Airplane(new Fuselage());
        airplane.setLeftEngine(new Engine());
        airplane.setRightEngine(new Engine());
        return airplane;
    }
}
```

### Sample `application.properties`
```properties
name=YA-93001
hp=840.12
flights=421
ready=true
```