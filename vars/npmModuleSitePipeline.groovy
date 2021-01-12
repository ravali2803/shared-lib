final sharedlib = library('sharedlib@master')
def call(Map pipelineParams) {

pipeline {
    agent any
    stages {
        stage('Compile') {
            steps {
                echo 'npm build'
            }
        }
        stage('Unit Test') {
            steps {
                echo 'npm test'
            }
        }
        stage('Code Analysis') {
            steps {
                echo 'sonar analysis'
            }
        }
        stage('Publish to Artifactory') {
            when {
                branch 'master'
            }
            steps {
                echo 'publish to npm module repo in artifactory'
            }
        }
    }
}
}    