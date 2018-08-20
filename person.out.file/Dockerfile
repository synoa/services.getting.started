FROM openjdk:8-jre-alpine
LABEL maintainer="Synoa GmbH <we@synoa.de>"

# This can be overriden and used configure the amount of Memory used by this Service
ENV _JAVA_OPTIONS="-Xms64m -Xmx128m"

# Start it this way, so Spring can access Environment Variables
ENTRYPOINT ["/usr/bin/java", "-jar", "/person.out.file.jar"]

# Set the Timezone to CET/CEST
RUN apk update
RUN apk add tzdata
RUN cp /usr/share/zoneinfo/Europe/Berlin /etc/localtime
RUN echo "Europe/Berlin" > /etc/timezone
RUN apk del tzdata
RUN rm -rf /var/cache/apk/*

# Add the Script and the Properties File for Git Version Information
ADD version.sh /
RUN chmod +x /version.sh
ADD target/git.properties /

# Add the compiled Jar and the default Properties
ADD target/person.out.file.jar /
ADD application.properties /
