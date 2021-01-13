final sharedlib = library('sharedlib@master')
def call(Map pipelineParams) {

pipeline {
    agent any
    developer = 'ravali'
    QA = 'ravali'
    PO = 'ravali'

    stages {
        stage('Compile') {
            steps {
                echo 'mvn clean install'
            }
        }
        stage('Unit Test') {
            steps {
                echo 'mvn test'
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
                echo 'publish to artifactory'
            }
        }
        stage('Dev Deploy') {
            when {
                branch 'master'
            }
            steps {
                echo 'deploy to Dev env'
            }
        }
        stage('Dev-->QA') {
            agent none
            when {
                branch 'master'
            }
            steps {
                input message: 'hey developer would you like to promote to QA env', ok: 'proceed abort', submitter: env.developer, submitterParameter: 'approver'
            }
        stage('QA Deploy') {
            when {
                branch 'master'
            }
            steps {
                echo 'QA deploy'
            }
        }    
        stage ('QA-->Staging') {
            agent none
            when {
                branch 'master'
            }
            steps {
                input message: 'hey QA Engineer would you like to promote to staging env', ok: 'proceed abort', submitter: env.QA, submitterParameter: 'approver'
            }  
        }
        stage('Staging Deploy') {
            when {
                branch 'master'
            }
            steps {
                echo 'staging deploy'
            }
        }
        stage ('Staging-->Production') {
            agent none
            when {
                branch 'master'
            }
            steps {
                input message: 'hey PO would you like to promote to prod env', ok: 'proceed abort', submitter: env.PO, submitterParameter: 'approver'
            }  
        }
        stage('Production Deploy') {
            when {
                branch 'master'
            }
            steps {
                echo 'Production deploy'
            }    
        }
    }
}
}
