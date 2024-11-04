# Use the latest Tomcat image as the base
FROM tomcat:latest

# Copy the WAR file to the Tomcat webapps directory
ADD target/damienspetitions.war /usr/local/tomcat/webapps/damienspetitions.war

# Expose the default Tomcat port
EXPOSE 8080

# Run Tomcat
CMD ["catalina.sh", "run"]
