# PulseNotify
Event driven notification based application
Java Version 17
Available profiles (local , dev)
Examples of activating profile using : 
	Maven : mvn spring-boot:run -Dspring-boot.run.profiles=local
	Cmd : java -jar pulsenotify.jar --spring.profiles.active=local
	
## Local Development with Docker

### Start infrastructure

```bash
docker compose up -d
```

### Stop infrastructure

```bash
docker compose down
```

### pgAdmin

http://localhost:5050

Email: admin@pulsenotify.com

Password: admin123
