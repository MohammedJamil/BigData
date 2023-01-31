# Traffic Analyzer

 Nous disposons d’un ensemble de capteurs installés sur le domaine universitaire. Ces capteurs permettent de détecter le passage d’un type de véhicule et du sens de son sens de circulation sur les différentes entrées de l’université.

 L’objectif du projet mettre en place un dashboard de visualisation des données des différents capteurs placés sur le campus universitaire après nettoyage et analyse de ces données.

---

## Nettoyage et analyse des données

D'abord il faut génerer le fichier .jar à partie du code source :

```bash
$ cd TrafficAnalyzer/
$ mvn package
```

Ensuite utilisez la commande ```scp``` pour transferer le .jar vers la gateway. Une fois sur la gateway executez les commandes suivantes :

- Pour lancer le nettoyage des données.

```bash
$ yarn jar TrafficAnalyzer-1.0-SNAPSHOT.jar org.traffic.DataNormalizer /user/auber/data_ple/citytraffic/ResultatCSV/ ./out
```

- Pour lancer les analyses.

```bash
$ yarn jar TrafficAnalyzer-1.0-SNAPSHOT.jar org.traffic.DataAnalyzer ./out/part-r-00000 ./out/analysis
```

## HBase

- Pour alimenter la HBase avec les résultats des analyses.

```bash
$ export HADOOP_CLASSPATH=`hadoop classpath`:`hbase mapredcp`:/etc/hbase/conf:/usr/hdp/3.0.0.0-1634/hbase/lib/*

$ yarn jar TrafficAnalyzer-1.0-SNAPSHOT.jar org.traffic.HBaseWriter ./out/analysis
```

- Pour afficher la liste des tables sur le HBase shell.

```bash
$ hbase shell
> list
```

- Pour afficher une table en particulier.

```bash
$ hbase shell
> scan 'namespace:TableName'
```

## Dashboard

Pour lancer le Dashboard et visualiser les résultats des analyses.

```bash
$ cd TrafficAnalyzer.Front
$ npm install
$ npm start
```
