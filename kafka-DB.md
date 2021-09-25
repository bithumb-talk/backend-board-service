####  :hammer_and_pick: 필요 툴, 주요 코드 :hammer_and_pick:

---



1. confluent 설치

```bash
curl -O http://packages.confluent.io/archive/6.1/confluent-community-6.1.0.tar.gz
tar xvf confluent-community-6.1.0.tar.gz
```



2. mariaDB jdbc드라이버 설치

```
wget https://repo1.maven.org/maven2/org/mariadb/jdbc/mariadb-java-client/2.7.2/mariadb-java-client-2.7.2.jar
```



---



- src connector

```
POST http://IP:8083/connectors
```

```json
{
    "name": "rds_test_source_connect",
    "config": {
        "connector.class": "io.confluent.connect.jdbc.JdbcSourceConnector",
        "connection.url": "jdbc:mysql://user-service.cprq7tgal4rf.us-east-1.rds.amazonaws.com:3306/bithumb",
        "connection.user": "",
        "connection.password": "",
        "mode": "incrementing",
        "incrementing.column.name": "user_no",
        "table.whitelist": "user",
        "topic.prefix": "rds_test_topic_",
        "tasks.max": "1"
    }
}
```



- sink connector

```
POST http://IP:8083/connectors
```

```json
{
    "name": "rds_test_sink_connect",
    "config": {
        "connector.class": "io.confluent.connect.jdbc.JdbcSinkConnector",
        "connection.url": "jdbc:mysql://board-service.cprq7tgal4rf.us-east-1.rds.amazonaws.com/bithumb",
        "connection.user": "admin",
        "connection.password": "adminrds",
        "auto.create": "true",
        "auto.evolve": "true",
        "delete.enabled": "false",
        "tasks.max": "1",
        "topics": "rds_test_topic_user",
        "name": "rds_test_sink_connect"   
    }
}
```

