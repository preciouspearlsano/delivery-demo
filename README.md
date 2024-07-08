# COMPANY STARTER KIT
```
Author: Precious Pearl
Email: pr3_cious_15@yahoo.com
```
# Common Dependencies Version
- Please read the Developers Manual Guide v1 for common framework at github.

# Introduction

### Pre-requisite
- Maven 3
- Adoptium JDK 17

### Maven run

// .m2 online pre-run

mvn clean install

// offline run 

mvn spring-boot:run -o -D spring.config.location="application-{env}.properties"

// Once adjust mvn offline or online inside batch command
RUN_DELIVERY.bat


### Other run

java -jar delivery-demo.jar

### To test

Please read the Developers Manual Guide v1 for test integration at github


### Back-end Convention

```
delivery-demo template
+-- main.../
 +-- config/
 +-- constants/
 +-- controller/external
 +-- controller/internal
 +-- dto/
 +-- exception/
 +-- repository
 +-- security
 +-- service
 +-- utils
 +-- utils/webclient
 +-- utils/listener
 +-- utils/command
 +-- DeliveryDemoApplication
+-- resources
 +-- ...mapper/
 +-- application.yaml
 +-- application-local.yaml
 +-- application-dev.yaml
 +-- application-sit.yaml
 +-- application-fnt.yaml
 +-- application-prod.yaml
 +-- logback-spring.xml
 +-- messages/
 	+--messages_en.properties
 +-- other cfg files 
+-- test/ 

```

## Features none
The company has documented developers helping library will release soon.

## Dependency Injection
-- Use `constructor injection` instead `field injection`
//bad
public class CompanyService {
	@AutoWired
	private CompanyRepository companyRepository;
}

//good
public class CompanyService {
	private final CompanyRepository companyRepository;
	public CompanyService(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}
}
	
-- Avoid single implementation interface
See YAGNI rules for more details

//bad
public interface CompanyService {
	List<Company> getCompany();\
}

public class CompanyServiceImp implements CompanyService {
	public List<Company> getCompany() {
		//add syntax here
	}
}
	
//good
public class CompanyService {
	public List<Company> getCompany() {
		//add syntax here
	}
}

## Controllers
-- Use `@RestController` for spring boot feature
//bad
@Controller
public class CompanyController {
	@ResponseBody
	@GetMapping("/company/{id}")
	public Company load(@PathVariable String id) {
		//add syntax here
	}
}

//good

@RestController
public class CompanyController {
	@GetMapping("/company/{id}")
	public Company load (@PathVariable String id) {
		//add syntax here
	}
}

-- Use `GetMapping`, `PostMapping` etc instead of `@RequestMapping`

//bad
public class CompanyController {
	@RequestMapping(method= RequestMethod.GET, value = "/company/{id}")
	public Company load(@PathVariable String id) {
		//add syntax here
	}
}


//good
@RestController
public class CompanyController {
	@GetMapping("/company/{id}")
	public Company load(@PathVariable String id){
		//add syntax here
	}
}

-- Simplify by making it by thin on your controller to avoid SRP violation

//bad
//good

# Services

-- use declare var to simplify calling code and avoid violation rule in sonarlint and sonarqube.

## Supports
For more information , please visit github Developers Guideline and or Training Videos <https://link>