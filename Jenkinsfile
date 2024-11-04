pipeline {
    agent any

    environment {
        MAVEN_HOME = "/usr/share/maven"
        JAVA_HOME = "/usr/lib/jvm/java-17-openjdk-amd64"
        EC2_USER = 'ubuntu'
        EC2_IP = 'ec2-51-20-7-166.eu-north-1.compute.amazonaws.com'
        DOCKER_IMAGE = 'damienspetitions'
        CONTAINER_NAME = 'damienspetitions-container'
        WAR_NAME = 'damienspetitions.war'
    }

    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'main', url: 'https://github.com/damienb1983/petitions.git'
            }
        }

        stage('Build') {
            steps {
                script {
                    echo "Building the project..."
                    sh "${MAVEN_HOME}/bin/mvn clean compile"
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    echo "Running tests..."
                    sh "${MAVEN_HOME}/bin/mvn test"
                }
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                script {
                    echo "Packaging the application..."
                    sh "${MAVEN_HOME}/bin/mvn package -DskipTests"
                    sh "mv target/*.war target/${WAR_NAME}"
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    echo "Building Docker image..."
                    sh "docker build -t ${DOCKER_IMAGE}:latest ."
                }
            }
        }

        stage('Deploy to EC2') {
            steps {
                script {
                    echo "Deploying Docker container to EC2..."

                    sshagent(['ec2-ssh-key']) {
                        sh """
                            ssh -o StrictHostKeyChecking=no ${EC2_USER}@${EC2_IP} << EOF
                                docker rm -f ${CONTAINER_NAME} || true
                                docker run -d --name ${CONTAINER_NAME} -p 8081:8080 ${DOCKER_IMAGE}:latest
                            EOF
                        """
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}