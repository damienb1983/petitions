# Use the official Tomcat image
FROM tomcat:10.1.5-jdk17

# Set the context path (e.g., "/damienspetitions") for the application
ENV CATALINA_OPTS="-Dserver.servlet.context-path=/damienspetitions"

# Copy the WAR file from the target directory to Tomcat's webapps folder
ADD target/*.war /usr/local/tomcat/webapps/damienspetitions.war

# Expose port 8080
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
