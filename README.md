# io-engine

Smart home automation engine designed to run on a Raspberry Pi. It acts as the central backend, receiving sensor events and executing actions across connected devices via MQTT and Modbus TCP.

## Stack

- Java 8, Spring Boot 2.6.14 (WAR packaging)
- MQTT (Spring Integration) — device communication bus
- Modbus TCP (jlibmodbus) — industrial device protocol
- SQLite + Flyway — persistent event/state storage
- JsonDB — JSON file-based storage for structure data
- Firebase Admin SDK — push notifications
- Pi4J — Raspberry Pi GPIO access

## Features

- **Zone & device management** — structure defined in `structure.yaml`: zones, sensors, blinds, lights, temperature controllers
- **Automation engine** — macros with conditional blocks, triggers on sensor events, cron schedules, and timers
- **Lighting control** — RGBW/LED scenes, brightness, color
- **Blind/shutter control** — per-zone blind positioning
- **Temperature control** — per-zone heating scenes (auto/manual/eco) with time ranges
- **MQTT integration** — publishes actions to `home/actions`, listens for triggers on `home/triggers`, supports Hermes intent topics (Rhasspy/Snips voice assistant)
- **Modbus TCP** — read/write registers on industrial controllers
- **Sound/FFT module** — audio analysis for club/party lighting effects
- **REST API** — UI action endpoint for manual control
- **Monitoring** — event log with token-authenticated search API
- **Firebase push notifications** — remote alerts

## Configuration

| File | Purpose |
|------|---------|
| `src/main/resources/application.yml` | MQTT broker, Modbus host, DB path, monitoring token |
| `src/main/resources/structure.yaml` | Home structure: zones, sensors, blinds, scenes, temperature |

Key settings in `application.yml`:

```yaml
mqtt:
  host: tcp://<broker>:1883
  client-id: io-engine
  action-topic: home/actions
  trigger-topic: home/triggers

modbus:
  host: 192.168.0.10

db:
  path: /home/pi/smart-db

monitoring:
  api:
    token: <your-token>
```

## Building & Deploying

```bash
# Build
mvn clean package

# Deploy to Raspberry Pi (Tomcat)
pscp target/io-engine-0.0.1-SNAPSHOT.war pi@pi-master:/home/pi/tomcat/webapps/io-engine.war
```

## Automation Macros

Macros are JSON-defined programs composed of blocks:

- **ConditionBlock** — if/else logic with comparators (AND/OR/NOT)
- **RunnerBlock** — actions: device control, scene change, blind move, Modbus write, global variable set, nested macro call, timer, shell command
- **Variable fetchers** — read device state, sensor activity, global variables, current time/month, Modbus registers

Triggers fire macros on sensor events. Cron jobs schedule macros on a time basis.

## Project Structure

```
src/main/java/.../ioengine/
  config/           — Spring config (MQTT, REST, scheduling, monitoring auth)
  database/         — SQLite, JsonDB, Flyway setup
  modbus/           — Modbus TCP client service
  module/
    action/         — REST API, action dispatching
    automation/     — Macros, triggers, timers, cron
    club/           — FFT-based audio/lighting effects
    device/         — Device state management
    monitoring/     — Event log & search API
    mqtt/           — MQTT message handling
    structure/      — Zone/sensor/device model loaded from YAML
```
