pipeline {
    agent any

    triggers {
        pollSCM('H/10 * * * *')  // vérifie toutes les minutes (à ajuster)
    }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/BethusLilian/altenShop.git', branch: 'main'
            }
        }

        stage('Install deps') {
            steps {
                sh 'mvn clean install'   // ou maven, gradle, etc
            }
        }

        stage('Unit tests') {
            steps {
                sh 'mvn test'     // adapte selon ta stack !
            }
        }
    }
}