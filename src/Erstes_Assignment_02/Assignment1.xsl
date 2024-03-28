<?xml version="1.0" encoding="UTF-8"?>
<xs:stylesheet version="1.0"
    xmlns:xs="http://www.w3.org/1999/XSL/Transform"
    xmlns="http://www.w3.org/1999/xhtml"
    xmlns:xt="assignment01.webprog.de">
    <xs:output method="xml"/> 
     <!-- Deklaration, wird als xHtml angezeigt, version 1.0 , deklaration siehe Vorlesung-->
     <!-- xt:... hier extra deklariert, da wir den namensraum bei xPath ausdrücken benötigen, 
     die knoten befinden sich in einem implizit deklarierten Default namensraum -->
   <xs:template match="/">
    	<html>
     	 <head>
        <title>Labordaten</title>
      	</head>
      	<body>
        	<h1>Labordaten</h1>
        <xs:call-template name="Inhaltsverzeichnis"/>
        <xs:apply-templates select="//xt:Labor"/>
        <!-- Rekursiver Aufruf von  Templates für jedes Labor wir das passende match Template aufgerufen und einzelnd bearbeitet-->
        <xs:call-template name="Softwaredaten"></xs:call-template>
  		<xs:call-template name="weitere_Geraete"></xs:call-template>
  		<xs:call-template name="Hersteller"></xs:call-template>
        <xs:call-template name="generateTable"></xs:call-template>
        <!-- Rest wir direkt gecallt, da es keine Knoten gibt, für die sie spezifisch ausgeführt werden sollen, sie
        sollen einfach ausgeführt werden an bestimmten Stellen obwohl sie nicht 
        als Knoten in der XML Datei vorhanden sind, ähnlich wie der Separator in der Vorlesung -->
      </body>
    </html>
  </xs:template> 
  <!-- Aufruf wird auf jedes Kind ausgeführt automatisch ausgeführt, Wurzelelement wird bearbeitet gibt Struktur des angezeigten wieder-->
  <xs:template name="generateTable">
  		<table border="1" id="Tabelle">
  			<tr>
  				<th>
					Labore  			
  				</th>
  				<th>
  					Rechner
  				</th>
  				<th>
  					Softwareprodukte
  				</th>
  				<th>
  					Rechner pro Labor
  				</th>
  				<th>
  					Rechner mit lizensierter
  					Software
  				</th>
  				<th>
  					Einzigartige Software
  				</th>
  				<th>
  					Anzahl der Lizenzen
  				</th>
  			</tr>
  		<!-- Überschriften -->
  		<xs:variable name="Labore" select="count(//xt:Labor)"/>
  		<xs:variable name="Rechner" select="count(//xt:Computer)"/>
  		<xs:variable name="Software" select="count(//xt:Software)"/>
  		<xs:variable name="Softwareunique" select="count(//xt:Software[not(.=preceding::xt:Software)])"/>
  		<!-- //xt:XX , wobei XX ein vorhandener Knoten ist, Sucht alle XX Knoten, count() zählt die Anzahl der Knoten-->
  		<!-- Softwareunique zählt alle Software Knoten die nicht identisch sind, heißt wenn 2 Knoten denselben Inhalt haben 
  		werden sie nur einmal gezählt -->
  		<xs:variable name="Uniquesoft" select="count(//xt:Software2[@lizensiert='false'])"/>
  		<!-- Alle nicht lizensierten Software2 Knoten-->
  		<xs:variable name="Soft2" select="count(//xt:Software2)"/>
  		<!-- Alle Software 2 Knoten -->
  		<xs:variable name="Lizensen" select="$Soft2 - $Uniquesoft"/>
  		<!-- Nötig es so zu machen, da Count bei default Werten Probleme macht und diese
  		nicht mitzählt, wenn sie nicht explizit gesetzt werden -->
  		<xs:variable name="RechnerproLabor" select="$Rechner div $Labore"/>
  		<xs:variable name="Rechnersoft" select="count(//xt:Computer/xt:Software2[@lizensiert='true'])"/>
  		<!-- Zähle alle Computer, die Software2 haben und bei denen irgendeine Software2 lizensiert ist-->
  		<!-- XPath Ausdrücke für Werte -->
  		<xs:variable name="Lizenzen" select="count(.)"/>
  			<tr>
  				<td><xs:value-of select="$Labore"/></td>
  				<td><xs:value-of select="$Rechner"/></td>
  				<td><xs:value-of select="$Software"/></td>
  				<td><xs:value-of select="$RechnerproLabor"/></td>	
  				<td><xs:value-of select="$Rechnersoft"/></td>
  				<td><xs:value-of select="$Softwareunique"/></td>
  				<td><xs:value-of select="$Lizensen"/></td>
  			</tr>
  		<!--  Tabellen Eintraege werden übernommen bzw ausgerechnet(div )-->
  		</table>
  	</xs:template>
  	<xs:template name="Inhaltsverzeichnis">
  		<h1>Inhaltsverzeichnis</h1>
  		<ul>
  		<xs:for-each select="//xt:Labor">
  		<!-- xt muss davor um auf den namensraum, indem sich jedes element der Datei befindet zuzugreifen, Default namensraum wird nicht von
  		Datei übernommen die Knoten müssen diesem Namensraum extra zugewiesen werden-->
        	<li><a href="#{generate-id()}">
        	<xs:value-of select="@Name"/></a></li>
     	 </xs:for-each>
     	 <!--for each nicht für Template Nutzung verwendet, sondern um alle Laborlinks zu erstellen, Templates werden alle rekursiv aufgerufen-->
  		<li><a href="#Software">Software</a></li>
  		<li><a href="#weiteres">Andere geräte</a></li>
  		<li><a href="#Hersteller">Hersteller</a></li>
  		<li><a href="#Tabelle">Tabelle</a></li>
  		</ul>
  <!-- Inhaltsverzeichnis Template, erzeugt Inhaltsverzeichnis und erschafft interne links-->
  	</xs:template>
	<xs:template match="xt:Labor">
  		<div id="{generate-id()}">
   	 	<h2><xs:value-of select="@Name"/></h2>
   	 	<h2>Raum: <xs:value-of select="@Raum"/></h2>
    	<h2>Rechner:</h2>
    	<xs:apply-templates select="xt:Computer"/>
    	<br/>
    	<h2>Software:</h2>
    	<xs:apply-templates select="xt:Software"/>
    	<br/>
    	<h2>Weitere Geräte:</h2>
    	<xs:apply-templates select="xt:weitere_Geraete"/>
  		</div>
	</xs:template>
	<xs:template match="xt:Software">
		<h2>SoftwareID:<xs:value-of select="@ID"/></h2>
    	<p>Softwarename:<xs:value-of select="xt:Softwarename"/></p>
    	<p>Beschreibung:<xs:value-of select="xt:Softwarebeschreibung"/></p>
    	<p>Besonderheiten:<xs:value-of select="xt:Softwarebesonderheiten"/></p>
	</xs:template>
<!-- Tempate wir durch apply-templates select="xt:Software" aufgerufen gibt einfach alle Daten der Software an -->
	<xs:template match="xt:Computer">
  		<li>
    	<h2>RechnerID:<xs:value-of select="@RechnerId"/></h2>
    	<p>Betriebssystem:<xs:value-of select="xt:Betriebssystem"/></p>
    	<p>Anschaffungsdatum:<xs:value-of select="xt:Anschaffung"/></p>
   		<p>Erweiterungen:<xs:value-of select="xt:Erweiterungen/xt:Beschreibung"/></p>
    	<p>Beschreibung:<xs:value-of select="xt:Rechnerbeschreibung"/></p>
    	<p>Besonderheiten:<xs:value-of select="xt:Rechnerbesonderheiten"/></p>
    	<xs:apply-templates select="xt:Software2">
      		<xs:sort select="@Id"/>
      <!-- über @ graifen wir auf das Attribut Id des rechners zu 
      Achse ist in diesem Moment der spezifische Rechner, für den das Template ausgeführt wird
      wir greifen auf die kinder des Knotens zu-->
    	</xs:apply-templates>
  		</li>
	</xs:template>

	<xs:template match="xt:Software2">
  		<li>
    	<xs:variable name="Liz" select="@Id"/>
    	<xs:choose>
      		<xs:when test="@lizensiert='false'">
      <!-- ist das lizensiert attribut auf false ist es nicht lizensirt anonsten schon, da der default Wert true ist , je
      nachdem wird xs:when (wenn es stimmt also lizensiert auf false steht) oder xs:otherwise
      (jeder anderer Fall entweder nichts oder auf true) wird lizensiert ausgegeben, zusätzlich wir die Id durch
      die variable Liz übergeben und angezeigt-->
        		<p>Software: <xs:value-of select="$Liz"/> (Nicht lizensiert)</p>
      		</xs:when>
      		<xs:otherwise>
        		<p>Software: <xs:value-of select="$Liz"/> (Lizensiert)</p>
      		</xs:otherwise>
    	</xs:choose>
  		</li>
	</xs:template>


	<xs:template match="xt:weitere_Geraete">
  		<li>
    	<p>ID: <xs:value-of select="@WeiteresID"/></p>
    	<p>Art:<xs:value-of select="xt:Art"/></p>
    	<p>Beschreibung:<xs:value-of select="xt:Beschreibung"/></p><br/>
  		</li>
	</xs:template>
<!-- Weitere geraete (Drucker etc) match template gibt einfach Daten aus -->


  <xs:template name="Softwaredaten">
  		<h1 id="Software">SoftwareListe</h1>
		<ul>
    	<xs:apply-templates select="//xt:Softwarename[not(.=preceding::xt:Softwarename)]">
    <!-- [not(.=preceding::xt:Softwarename)], sorgt dafür, dass alle Namen nur einmal ausgegeben werden, gibt es in dem Dokument mehrere
    Softwarearten mit demselben Namen oder gibt es sie in verschiedenen Laboren wird jeder Einzigartige Name nur einmal ausgegeben, da
    wenn ein Vorgänger Geschwister Knoten existiert der denselben Namen hat, wird der aktuelle nicht ausgegeben, da not[] sagt, dass diese 
    mit Vorgänger Geschwistern nicht übergeben werden, bzw nicht ausgewählt werden-->
      		<xs:sort order="ascending"/>
      <!-- Aufsteigend sortiert, zur Sicherheit zugeschrieben, Standardmässig auf text gesetzt, da muss nicht ausgewählt werden
      sort zählt für die auswahl von apply-templates -->
    	</xs:apply-templates>
  		</ul>
	</xs:template>

	<xs:template match="xt:Softwarename">
  		<li>
  		<xs:value-of select="."/>
  		</li>	   
  	</xs:template>
  	<xs:template name="weitere_Geraete">
  		<h1 id="weiteres">Weitere Geräte</h1>
  		<ul>
  		<xs:apply-templates select="//xt:Art[not(.=preceding::xt:Art)]">
  <!-- Art als Artd es Gerätes die Liste enthällt jede Art von gerät nur einmal-->
    		<xs:sort order="ascending"/>
  		</xs:apply-templates>
  		</ul>
	</xs:template>

	<xs:template match="xt:Art">
  		<li>
   		<xs:value-of select="."/>
  		</li>
 	</xs:template>
 <!-- Gibt Text des xt:Art Knotens aus-->
  	<xs:template name="Hersteller">
  		<h1 id="Hersteller">Hersteller</h1>
  		<ul>
    	<xs:apply-templates select="//xt:hersteller[not(.=preceding::xt:hersteller)]">
      		<xs:sort order="ascending"/>
    	</xs:apply-templates>
  		</ul>
	</xs:template>
<!-- Erstellt Liste aus hersteller Knoten, ohne Duplikate und alphabetsich sortiert über sort 	
für jeden Knoten einzeld wird das match templates ausgeführt, dieses gibt einfach den Inhalt aus (Text)-->

	<xs:template match="xt:hersteller">
  	<li>
    <xs:value-of select="."/>
  	</li>
	</xs:template>
</xs:stylesheet>