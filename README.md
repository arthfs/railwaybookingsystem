Prerequisites

    Java 21 

    Eclipse IDE 

    MySQL 8.0+ 

    Apache Tomcat 10.x 

 **   Configure credentials:**
 edit railwaysystem/src/main/resources/config.properties
 db.url=jdbc:mysql://localhost:3306/railwaybookingsystem
 db.user=your_username  # e.g., "root"
 db.password=your_password

 Run the Application

    Start Tomcat:

       In eclipse, Go to Servers view → Right-click Tomcat → Start.

    Access the app:
    Open: http://localhost:8080/railwaysystem

    **Users you can test**
 (customer_service)   username :jsmith        password: service123
 (customer)   username :mp112                 password: qaz12
 (mannager)   username :ljohnson              password: manage123
