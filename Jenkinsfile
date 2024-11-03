pipeline {
    agent any

    environment {
        MAVEN_HOME = "/usr/share/maven"
        JAVA_HOME = "/usr/lib/jvm/java-17-openjdk-amd64"
        EC2_USER = 'ubuntu'
        EC2_IP = 'ec2-51-20-7-166.eu-north-1.compute.amazonaws.com'
        WAR_NAME = 'damienspetitions.war'
        APP_DIR = "/home/${EC2_USER}/apps"
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
                    sh "${MAVEN_HOME}/bin/mvn package"
                    sh "ls -l target/${WAR_NAME}"
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    echo "Deploying the application to EC2..."

                    // Ensure the application directory exists on EC2
                    sshagent(['ec2-ssh-key']) {
                        sh "ssh -o StrictHostKeyChecking=no ${EC2_USER}@${EC2_IP} 'mkdir -p ${APP_DIR}'"

                        // Copy WAR file to EC2 instance
                        sh "scp -o StrictHostKeyChecking=no target/${WAR_NAME} ${EC2_USER}@${EC2_IP}:${APP_DIR}/"

                        // Stop the existing application (you may need to implement this)
                        sh "ssh -o StrictHostKeyChecking=no ${EC2_USER}@${EC2_IP} 'pkill -f ${WAR_NAME}'"

                        // SSH into the EC2 instance and run the application
                        sh "ssh -o StrictHostKeyChecking=no ${EC2_USER}@${EC2_IP} 'nohup java -jar ${APP_DIR}/${WAR_NAME} > ${APP_DIR}/app.log 2>&1 &'"
                    }
                }
            }
        }
    }

    post {
        success {
            echo 'Build succeeded!'
        }
        failure {
            echo 'Build failed!'
        }
    }
}