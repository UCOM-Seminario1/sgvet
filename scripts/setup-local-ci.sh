#!/bin/bash

# Script para configurar Jenkins y SonarQube local
# Para el examen final - 5 puntos adicionales

echo "🚀 Configurando Jenkins y SonarQube Local"
echo "=========================================="

# Verificar que Docker esté instalado
if ! command -v docker &> /dev/null; then
    echo "❌ Docker no está instalado. Por favor instala Docker Desktop."
    exit 1
fi

if ! command -v docker-compose &> /dev/null; then
    echo "❌ Docker Compose no está instalado."
    exit 1
fi

echo "✅ Docker encontrado"

# Crear directorios necesarios
mkdir -p scripts
mkdir -p jenkins/jobs
mkdir -p sonar-project.properties

# Iniciar servicios
echo "🐳 Iniciando contenedores..."
docker-compose -f docker-compose-local.yml up -d

# Esperar a que los servicios estén listos
echo "⏳ Esperando a que los servicios estén listos..."
sleep 30

# Obtener password inicial de Jenkins
echo "🔑 Obteniendo password inicial de Jenkins..."
JENKINS_PASSWORD=$(docker exec jenkins-local cat /var/jenkins_home/secrets/initialAdminPassword 2>/dev/null)

# Mostrar información de acceso
echo ""
echo "✅ SERVICIOS INICIADOS EXITOSAMENTE"
echo "=================================="
echo ""
echo "🔧 Jenkins:"
echo "   URL: http://localhost:8080"
echo "   Usuario: admin"
echo "   Password inicial: $JENKINS_PASSWORD"
echo ""
echo "📊 SonarQube:"
echo "   URL: http://localhost:9000"
echo "   Usuario: admin"
echo "   Password: admin"
echo ""
echo "🗄️ PostgreSQL:"
echo "   Host: localhost:5432"
echo "   DB: sonarqube"
echo "   Usuario: sonarqube"
echo ""

# Crear archivo de configuración SonarQube
cat > sonar-project.properties << EOF
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
EOF

echo "📁 Archivo sonar-project.properties creado"

# Crear job de Jenkins automático
cat > jenkins/jobs/sgvet-proveedor-job.xml << 'EOF'
<?xml version='1.1' encoding='UTF-8'?>
<project>
  <actions/>
  <description>Job automático para el módulo proveedor de SgVet</description>
  <keepDependencies>false</keepDependencies>
  <properties/>
  <scm class="hudson.plugins.git.GitSCM">
    <configVersion>2</configVersion>
    <userRemoteConfigs>
      <hudson.plugins.git.UserRemoteConfig>
        <url>https://github.com/tu-usuario/sgvet.git</url>
      </hudson.plugins.git.UserRemoteConfig>
    </userRemoteConfigs>
    <branches>
      <hudson.plugins.git.BranchSpec>
        <name>*/feature/proveedores-consultar-proveedores-pruebas_unitarias</name>
      </hudson.plugins.git.BranchSpec>
    </branches>
  </scm>
  <canRoam>true</canRoam>
  <disabled>false</disabled>
  <blockBuildWhenDownstreamBuilding>false</blockBuildWhenDownstreamBuilding>
  <blockBuildWhenUpstreamBuilding>false</blockBuildWhenUpstreamBuilding>
  <triggers>
    <hudson.triggers.SCMTrigger>
      <spec>H/5 * * * *</spec>
      <ignorePostCommitHooks>false</ignorePostCommitHooks>
    </hudson.triggers.SCMTrigger>
  </triggers>
  <concurrentBuild>false</concurrentBuild>
  <builders>
    <hudson.tasks.Shell>
      <command>
#!/bin/bash
echo "🚀 Iniciando build del módulo proveedor"

cd proveedor

# Limpiar y compilar
mvn clean compile

# Ejecutar tests
mvn test

# Generar reporte de cobertura
mvn jacoco:report

# Análisis SonarQube
mvn sonar:sonar \
  -Dsonar.projectKey=sgvet-proveedor-local \
  -Dsonar.host.url=http://sonarqube:9000 \
  -Dsonar.login=admin \
  -Dsonar.password=admin

echo "✅ Build completado"
      </command>
    </hudson.tasks.Shell>
  </builders>
  <publishers/>
  <buildWrappers/>
</project>
EOF

echo "📋 Job de Jenkins creado"

# Instrucciones finales
echo ""
echo "🎯 PRÓXIMOS PASOS:"
echo "=================="
echo "1. Abre http://localhost:8080 en tu navegador"
echo "2. Usa el password: $JENKINS_PASSWORD"
echo "3. Instala plugins sugeridos"
echo "4. Crea un job que ejecute: mvn clean test jacoco:report sonar:sonar"
echo "5. Abre http://localhost:9000 para SonarQube"
echo "6. Toma screenshots de ambos para el examen"
echo ""
echo "🛠️ Para detener los servicios:"
echo "   docker-compose -f docker-compose-local.yml down"
echo ""
echo "✅ ¡Configuración completada!"