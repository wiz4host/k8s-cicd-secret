import jenkins.model.*
//import groovy.json.JsonSlurper

pipeline {
    agent {label 'master'}
    
     parameters {
        string(name: 'K8SSECRETID', defaultValue: '', description: 'mention secretID')
    }
    
    
    stages {

        stage('git pull') {
            steps{
                git branch: 'main', url: 'https://github.com/wiz4host/k8s-cicd-secret.git'
                sh 'ls -ltr'
            }
        }


       stage('read-un') {
            steps {
                    script {
                        def config                = readJSON file: 'k8s-secrets-metadata2.yaml'
                        //def config                = jsonSlurper.parse(new File('k8s-secrets-metadata.yaml'))
                        def K8SSECRETCN           = config.AllSecret.find { it.ID == lookupid }.ClusterName
                        def K8SNAMESPACE          = config.AllSecret.find { it.ID == lookupid }.NameSpace
                        def K8SSECRET_CREATE_UN   = config.AllSecret.find { it.ID == lookupid }.SecretsID.CREATE.UN
                        def K8SSECRET_DELETE_DEL  = config.AllSecret.find { it.ID == lookupid }.SecretsID.DELETE.UN

                        if (K8SSECRET_CREATE_UN) {
                            K8SSECRET_CREATE_UN.each { item ->
                                withCredentials(
                                [ usernamePassword(
                                    credentialsId: item,
                                    passwordVariable: 'SECRET_PWD',
                                    usernameVariable: 'SECRET_UN'
                                )]){
                                    def K8SSECRETID = item
                                    sh """

                                        echo $SECRET_UN > secret-cre-un.txt
                                        echo $SECRET_PWD >> secret-cre-un.txt
                                        ls -ltr
                                        cat secret-cre-un.txt
                                        
                                        echo "kubectl -n $K8SNAMESPACE create secret generic $K8SSECRETID \
                                            --from-literal=username=$SECRET_UN \
                                            --from-literal=password=$SECRET_PWD"
                                    """
                                    archiveArtifacts artifacts: 'secret-un.txt'
                            
                                }
                            }
                        }

                        if (K8SSECRET_DELETE_UN) {
                            K8SSECRET_DELETE_UN.each { item ->
                                withCredentials(
                                [ usernamePassword(
                                    credentialsId: item,
                                    passwordVariable: 'SECRET_PWD',
                                    usernameVariable: 'SECRET_UN'
                                )]){
                                    def K8SSECRETID = item
                                    sh """

                                        echo $SECRET_UN > secret-del-un.txt
                                        echo $SECRET_PWD >> secret-del-un.txt
                                        ls -ltr
                                        cat secret-un.txt
                                        
                                        echo "kubectl -n $K8SNAMESPACE create secret generic $K8SSECRETID \
                                            --from-literal=username=$SECRET_UN \
                                            --from-literal=password=$SECRET_PWD"
                                    """
                                    archiveArtifacts artifacts: 'secret-un.txt'
                            
                                }
                            }
                        }
                    }
                } 
        }

    }
}
