# COMPANY STARTER KIT
```
Author: Precious Pearl Sano
Email: pr3_cious_15@yahoo.com
```
# Common Dependencies Version
- Please read the Developers Manual Guide v1 for common framework at github.

# Introduction

### Local Deployment Pre-requisite
- Maven 3
- Adoptium JDK 17
- SpringBoot 3.3.3

### Advance Deployment Pre-requisite
- Kubernetes cluster up and running.
- `kubectl` command-line tool installed and configured to access your Kubernetes cluster.
- Docker image for the application pushed to a container registry (e.g., DockerHub).

### Maven run

// .m2 online pre-run

mvn clean install

// offline run 

mvn spring-boot:run -o -D spring.config.location="application-{env}.properties"

// Once adjust mvn offline or online inside batch command
RUN_DEMO.bat


### Other run

java -jar note-demo.jar

### To test

Please read the Developers Manual Guide v1 for test integration at github


### Back-end Convention

```
note-demo template
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
 +-- NoteDemoApplication
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

### Advance Deployment Option

1. **Prepare the Kubernetes YAML files**

   Ensure that you have the `note-demo-deployment.yaml` file saved in your `k8s` folder. This YAML file contains the configuration for deploying the Note Demo service.

2. **Apply the Deployment Configuration**

   Apply the Kubernetes deployment configuration to your cluster by running:

   ```bash
   kubectl apply -f k8s/note-demo-deployment.yaml
   
3. **Verify Deployment** 
 
   kubectl get deployments
   
   kubectl get services

4. **Access the application**    

   kubectl get services note-demo-service

4. **Troubleshooting**  

   Check Logs:

   kubectl logs <pod-name>
   
   Describe Resources:
   
   kubectl describe deployment note-demo-deployment
   
   kubectl describe service note-demo-service
   
   

## Supports
For more information , please visit github Developers Guideline and or Training Videos <https://link>