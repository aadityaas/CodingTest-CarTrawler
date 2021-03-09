# CodingTest-CarTrawler

**This is a spring boot stand-alone application, when run renders list of cars as per given in requirement document.**
 I chose SpringBoot to develop this app because that comes handy when developing standalone app and it provides support for command line runner as well.

**Pre-requisite to run this application:**

You need to have maven installed on your machine on which you want to run this application.
Follow steps given at https://maven.apache.org/install.html to install maven(if required)

**Steps to run application:**

1.) Open command prompt in administrative mode

2.) Navigate to the project root directory(where pom.xml resides).
	e.g. :  cd  C:\Software\Workspace-Eclipse2020\CodingTest-CarTrawler
	
3.) run maven command <mvn clean package>
	This command will:
	- remove any pre existing directory
	- run Test
	- package executable jar and place that under target folder
	
If you face any issue with the test you can skip the test by below command:
	<mvn clean install -Dmaven.test.skip=true>

4.) To run the application, execute below command:
      <mvn spring-boot:run> 
      
**The application is generating two list:**

**First List:**(Generates correct result as per requirement given in Objectives)
1.) Does not include any duplicate
2.) Sorted based on corporate cars appear at the top. Note corporate cars are those supplied by AVIS, BUDGET, ENTERPRISE, FIREFLY, HERTZ, SIXT, THRIFTY
3.) Sorted within both the corporate and non-corporate groups, cars into “mini”, “economy”, “compact” and “other” based on SIPP beginning with M, E, C respectively.
4.) Sorted within each group on low-to-high price
5.) List displayed on screen.

**Second List:**
Tried implementing as given in point 3:
 - If time allows, include an optional step to remove all FuelType.FULLFULL cars that are priced above the median price within their groups. Hint: Google “Median Price”.
 
 I believe my solution is not perfect because it's calculating median of all cars(I think the requirement is to get median in each group) having FuelType FULLFULL.
 And then it's removing those cars which are having more rental than the median. Due to time constraint I had to stop it at this.



