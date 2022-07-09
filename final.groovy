import groovy.json.JsonSlurper


def getsecrets(lookupid, secrettype) {
    def jsonSlurper = new JsonSlurper()
    //lookupid = "C01N02"

    def config = jsonSlurper.parse(new File('data.json'))

    def result_ns = config.AllSecret.find { it.ID == lookupid }.NameSpace
    def val_id            = config.AllSecret.find { it.ID == lookupid }.ID
    def val_clustname     = config.AllSecret.find { it.ID == lookupid }.ClusterName
    def val_ns            = config.AllSecret.find { it.ID == lookupid }.NameSpace
    def val_secret_un     = config.AllSecret.find { it.ID == lookupid }.SecretsID.UN
    def val_secret_file   = config.AllSecret.find { it.ID == lookupid }.SecretsID.FILE
    def val_secret_text   = config.AllSecret.find { it.ID == lookupid }.SecretsID.TEXT
    def val_secret_ssh    = config.AllSecret.find { it.ID == lookupid }.SecretsID.SSH
    def val_secret_docker = config.AllSecret.find { it.ID == lookupid }.SecretsID.DOCKER
    //def val_namespace     =config.AllSecret.find  { it.ID == lookupid .SecretsID.NameSpace}

    Map myconfig = [:]
    Map defaultconfig = [id:"null",clustname:"null", namespace:"null",secretun:"null",secrettext:"null",secretfile:"null",secretssh:"null",namespace:"null"]
    myconfig << defaultconfig
    //println(myconfig)

    Map myvalues = [
        id: val_id,
        clustname: val_clustname,
        namespace:  val_ns,
        secretun: val_secret_un,
        secretfile: val_secret_file,
        secrettext: val_secret_text,
        secretssh: val_secret_ssh,
        secretdocker: val_secret_docker
        //secretnamespace: val_ns
    ]

    myconfig.id            = myvalues.id
    myconfig.clustname     = myvalues.clustname
    myconfig.namespace     = myvalues.namespace
    myconfig.secretun      = myvalues.secretun
    myconfig.secretfile    = myvalues.secretfile
    myconfig.secrettext    = myvalues.secrettext
    myconfig.secretssh     = myvalues.secretssh
    myconfig.secretdocker  = myvalues.secretdocker
    //myconfig.secretnamespace     = myvalues.secretnamespace


    // println(myconfig)
    // println(myconfig.secretun)
    // println(myconfig.secretfile)
    // println(myconfig.secrettext)
    // println(myconfig.secretssh)
    // println(myconfig.secretdocker)
    //println(secrettype)

    if (secrettype == "UN"){return myconfig.secretun}
    if (secrettype == "FILE"){return myconfig.secretfile}
    if (secrettype == "TEXT"){return myconfig.secrettext}
    if (secrettype == "SSH"){return myconfig.secretssh}
    if (secrettype == "DOCKER"){return myconfig.secretdocker}
    //if(secrettype == "NameSpace"){return myconfig.secretnamespace}

}


lookupid = "C01N02"
secretun = getsecrets(lookupid, "UN")
println("SECRET UN for " + lookupid + " :" + secretun)

secretfile = getsecrets(lookupid, "FILE")
println("SECRET FILE for " + lookupid + " :" + secretfile)

secrettext = getsecrets(lookupid, "TEXT")
println("SECRET TEXT for " + lookupid + " :" + secrettext)

secretssh = getsecrets(lookupid, "SSH")
println("SECRET SSH: for " + lookupid + " :" + secretssh)

secretdocker = getsecrets(lookupid, "DOCKER")
println("SECRET DOCKER for " + lookupid + " :" + secretdocker)

// secretnamespace = getnamespace(lookupid,"NameSpace")
// println("NameSpace for"+ lookupid+":"+secretnamespace)