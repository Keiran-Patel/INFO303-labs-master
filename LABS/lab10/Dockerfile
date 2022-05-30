############# Build Stage #############

# start with official Gradle base image
# Gradle 7, JDK 17, Alpine Linux
FROM gradle:7-jdk17-alpine as builder

# run install tasks as root
USER root

# install Git
RUN apk --no-cache add git

# run remainder of tasks as the unprivileged 'gradle' user
USER gradle

# clone repo
RUN git clone --quiet https://isgb.otago.ac.nz/infosci/git/INFO303/shopping.git

# switch into the repository directory
WORKDIR shopping

# checkout the release tag
RUN git checkout --quiet release

# add a volume for gradle home
VOLUME "/home/gradle/.gradle"

# build stand-alone version of project
RUN gradle --no-daemon --quiet installDist

############# Run Stage #############

# Start with the much smaller JRE rather than JDK
FROM eclipse-temurin:17-alpine

# copy the Gradle build output from the builder stage
COPY --from=builder /home/gradle/shopping/build/install /home/deployment

# switch into the stand-alone service folder
WORKDIR /home/deployment

# limit the Java heap size, and set default port
ENV \
    _JAVA_OPTIONS=-Xmx256M \
    PORT=8080

# the port that the service is using
EXPOSE $PORT

# command to start the service
CMD shopping/bin/shopping $PORT