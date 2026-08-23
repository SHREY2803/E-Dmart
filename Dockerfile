# ==========================================
# E-Dmart - Tomcat 10 Deployment
# ==========================================

FROM tomcat:10.1-jdk17-temurin

# Remove default Tomcat applications
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy web application
COPY src/main/webapp/ /usr/local/tomcat/webapps/ROOT/

# Copy Java source temporarily
COPY src/main/java/ /tmp/java-src/

# Create compiled classes directory
RUN mkdir -p /usr/local/tomcat/webapps/ROOT/WEB-INF/classes

# Compile Java source
RUN javac \
    -cp "/usr/local/tomcat/lib/servlet-api.jar:/usr/local/tomcat/webapps/ROOT/WEB-INF/lib/*" \
    -d /usr/local/tomcat/webapps/ROOT/WEB-INF/classes \
    $(find /tmp/java-src -name "*.java")

# Remove source code
RUN rm -rf /tmp/java-src

# Default Tomcat port
EXPOSE 8080

# Start Tomcat using Railway's PORT
CMD sed -i "s/port=\"8080\"/port=\"${PORT:-8080}\"/" \
    /usr/local/tomcat/conf/server.xml && \
    catalina.sh run