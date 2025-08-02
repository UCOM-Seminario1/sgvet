# Script para configurar Jenkins y SonarQube Local en Windows
# Para el examen final - 5 puntos adicionales

Write-Host "🚀 Configurando Jenkins y SonarQube Local" -ForegroundColor Green
Write-Host "==========================================" -ForegroundColor Green

# Verificar que Docker esté instalado
if (-not (Get-Command docker -ErrorAction SilentlyContinue)) {
    Write-Host "❌ Docker no está instalado. Por favor instala Docker Desktop." -ForegroundColor Red
    exit 1
}

if (-not (Get-Command docker-compose -ErrorAction SilentlyContinue)) {
    Write-Host "❌ Docker Compose no está instalado." -ForegroundColor Red
    exit 1
}

Write-Host "✅ Docker encontrado" -ForegroundColor Green

# Crear directorios necesarios
New-Item -ItemType Directory -Force -Path "scripts" | Out-Null
New-Item -ItemType Directory -Force -Path "jenkins\jobs" | Out-Null

# Iniciar servicios
Write-Host "🐳 Iniciando contenedores..." -ForegroundColor Yellow
docker-compose -f docker-compose-local.yml up -d

# Esperar a que los servicios estén listos
Write-Host "⏳ Esperando a que los servicios estén listos..." -ForegroundColor Yellow
Start-Sleep -Seconds 30

# Obtener password inicial de Jenkins
Write-Host "🔑 Obteniendo password inicial de Jenkins..." -ForegroundColor Yellow
try {
    $JenkinsPassword = docker exec jenkins-local cat /var/jenkins_home/secrets/initialAdminPassword 2>$null
} catch {
    $JenkinsPassword = "No disponible - revisar manualmente"
}

# Mostrar información de acceso
Write-Host ""
Write-Host "✅ SERVICIOS INICIADOS EXITOSAMENTE" -ForegroundColor Green
Write-Host "==================================" -ForegroundColor Green
Write-Host ""
Write-Host "🔧 Jenkins:" -ForegroundColor Cyan
Write-Host "   URL: http://localhost:8080" -ForegroundColor White
Write-Host "   Usuario: admin" -ForegroundColor White
Write-Host "   Password inicial: $JenkinsPassword" -ForegroundColor White
Write-Host ""
Write-Host "📊 SonarQube:" -ForegroundColor Cyan
Write-Host "   URL: http://localhost:9000" -ForegroundColor White
Write-Host "   Usuario: admin" -ForegroundColor White
Write-Host "   Password: admin" -ForegroundColor White
Write-Host ""
Write-Host "🗄️ PostgreSQL:" -ForegroundColor Cyan
Write-Host "   Host: localhost:5432" -ForegroundColor White
Write-Host "   DB: sonarqube" -ForegroundColor White
Write-Host "   Usuario: sonarqube" -ForegroundColor White
Write-Host ""

# Crear archivo de configuración SonarQube
$sonarConfig = @"
# Configuración del proyecto para SonarQube local
sonar.projectKey=sgvet-proveedor-local
sonar.projectName=SgVet Proveedor Local
sonar.projectVersion=1.0
sonar.sources=src/main/java
sonar.tests=src/test/java
sonar.java.binaries=target/classes
sonar.java.test.binaries=target/test-classes
sonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
sonar.host.url=http://localhost:9000
sonar.login=admin
sonar.password=admin
"@

$sonarConfig | Out-File -FilePath "sonar-project.properties" -Encoding UTF8
Write-Host "📁 Archivo sonar-project.properties creado" -ForegroundColor Green

# Crear script de ejecución de tests
$testScript = @"
@echo off
echo 🧪 Ejecutando tests y análisis...

cd proveedor

echo 🧹 Limpiando proyecto...
mvn clean

echo 🔨 Compilando...
mvn compile

echo 🧪 Ejecutando tests...
mvn test

echo 📊 Generando reporte de cobertura...
mvn jacoco:report

echo 📈 Enviando a SonarQube...
mvn sonar:sonar -Dsonar.projectKey=sgvet-proveedor-local -Dsonar.host.url=http://localhost:9000 -Dsonar.login=admin -Dsonar.password=admin

echo ✅ Análisis completado
pause
"@

$testScript | Out-File -FilePath "scripts\run-tests-and-sonar.bat" -Encoding UTF8
Write-Host "📋 Script de tests creado: scripts\run-tests-and-sonar.bat" -ForegroundColor Green

# Instrucciones finales
Write-Host ""
Write-Host "🎯 PRÓXIMOS PASOS:" -ForegroundColor Yellow
Write-Host "=================="
Write-Host "1. Abre http://localhost:8080 en tu navegador"
Write-Host "2. Usa el password: $JenkinsPassword"
Write-Host "3. Instala plugins sugeridos"
Write-Host "4. Ejecuta: scripts\run-tests-and-sonar.bat"
Write-Host "5. Abre http://localhost:9000 para SonarQube"
Write-Host "6. Toma screenshots de ambos para el examen"
Write-Host ""
Write-Host "🛠️ Para detener los servicios:" -ForegroundColor Red
Write-Host "   docker-compose -f docker-compose-local.yml down"
Write-Host ""
Write-Host "✅ ¡Configuración completada!" -ForegroundColor Green

# Abrir URLs automáticamente
Start-Process "http://localhost:8080"
Start-Process "http://localhost:9000"