//import jenkins.model.*
//import groovy.json.JsonSlurper

def lookupid = params.K8SSECRETID
def SOURCE_DATA_JSONFILE = "k8s-secrets-metadata-v2.json"

pipeline {
    agent {label 'master'}
    
     parameters {
        string(name: 'K8SSECRETID', defaultValue: 'C01N01', description: 'mention secretID')
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
                        def config = readJSON(text: readFile("./"+SOURCE_DATA_JSONFILE).trim())
                        def K8SSECRETCN           = config.AllSecret.find { it.ID == lookupid }.ClusterName
                        def K8SNAMESPACE          = config.AllSecret.find { it.ID == lookupid }.NameSpace
                        def K8SSECRET_CREATE_UN   = config.AllSecret.find { it.ID == lookupid }.SecretsID.CREATE.UN
                        def K8SSECRET_DELETE_UN  = config.AllSecret.find { it.ID == lookupid }.SecretsID.DELETE.UN


                        
                        if (K8SSECRET_CREATE_UN){
                            K8SSECRET_CREATE_UN.each { val ->
                                withCredentials(
                                    [   usernamePassword(
                                        credentialsId: val,
                                        passwordVariable: 'SECRET_PWD',
                                        usernameVariable: 'SECRET_UN'
                                    )]
                                ){
                                    def K8SSECRETID = val
                                    def SECRETCREUNARCHFILE = "secret-cre-un-"+K8SSECRETID+".txt"
                                    println(K8SSECRETID)
                                    sh """
                                        echo $SECRET_UN > ${SECRETCREUNARCHFILE}
                                        echo $SECRET_PWD >> ${SECRETCREUNARCHFILE}
                                        #ls -ltr
                                        #cat ${SECRETCREUNARCHFILE}
                                        echo "kubectl -n $K8SNAMESPACE create secret generic $K8SSECRETID --from-literal=username=$SECRET_UN --from-literal=password=$SECRET_PWD"
                                    """
                                    archiveArtifacts artifacts: SECRETCREUNARCHFILE
                                }
                                
                            }
                        }

                        if (K8SSECRET_DELETE_UN){
                            K8SSECRET_DELETE_UN.each { val ->
                                withCredentials(
                                    [   usernamePassword(
                                        credentialsId: val,
                                        passwordVariable: 'SECRET_PWD',
                                        usernameVariable: 'SECRET_UN'
                                    )]
                                ){
                                    def K8SSECRETID = val
                                    def SECRETDELUNARCHFILE = "secret-cre-un-"+K8SSECRETID+".txt"
                                    println(K8SSECRETID)
                                    sh """
                                        echo $SECRET_UN > ${SECRETDELUNARCHFILE}
                                        echo $SECRET_PWD >> ${SECRETDELUNARCHFILE}
                                        #ls -ltr
                                        #cat ${SECRETDELUNARCHFILE}
                                        echo "kubectl -n $K8SNAMESPACE delete secret generic $K8SSECRETID --from-literal=username=$SECRET_UN --from-literal=password=$SECRET_PWD"
                                    """
                                    archiveArtifacts artifacts: SECRETDELUNARCHFILE
                                }
                                
                            }
                        }
                        
                    }
                }
            }
        }


}
