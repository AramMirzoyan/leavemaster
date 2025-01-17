CREATE DATABASE IF NOT EXISTS keycloak_db;
CREATE DATABASE IF NOT EXISTS leavemaster;

CREATE USER IF NOT EXISTS 'keycloaks' IDENTIFIED BY 'keycloak';
CREATE USER IF NOT EXISTS 'leavemaster' IDENTIFIED BY 'leavemaster';

GRANT ALL PRIVILEGES ON keycloak_db.* TO 'keycloaks';
GRANT ALL PRIVILEGES ON leavemaster.* TO 'leavemaster';
FLUSH PRIVILEGES;



