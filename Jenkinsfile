pipeline {
    agent any

    environment {
        MAVEN_HOME = "/usr/share/maven"
        JAVA_HOME = "/usr/lib/jvm/java-17-openjdk-amd64"
        EC2_USER = 'ubuntu'
        EC2_IP = '51.20.7.166'
        KEY_PATH = '/Users/damienbarrett/Downloads/AmazonEc2.pem'
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
                    sh "${MAVEN_HOME}/bin/mvn package"
                    sh "ls -l target/${WAR_NAME}"
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    echo "Deploying the application to EC2..."

                    // Copy WAR file to EC2 instance
                    sh "scp -i ${KEY_PATH} target/${WAR_NAME} ${EC2_USER}@${EC2_IP}:/home/${EC2_USER}/apps/"

                    // SSH into the EC2 instance and run the application
                    sh "ssh -i ${KEY_PATH} ${EC2_USER}@${EC2_IP} 'nohup java -jar /home/${EC2_USER}/${WAR_NAME} > /home/${EC2_USER}/app.log 2>&1 &'"
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