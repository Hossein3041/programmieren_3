<?xml version="1.0" encoding="UTF-8"?>
<xs:stylesheet version="1.0"
    xmlns:xs="http://www.w3.org/1999/XSL/Transform"
    xmlns="http://www.w3.org/1999/xhtml"
    xmlns:xt="assignment01.webprog.de">
     <xs:output method="xml"/> 
    <xs:template match="/">
    <html>
      <head>
        <title>Labordaten</title>
      </head>
      <body>
        <h1>Labordaten</h1>
        <xs:call-template name="generateTable"/>
      </body>
    </html>
  </xs:template> 
  <xs:template name="generateTable">
  	<table border="1">
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
  		</tr>
  		<xs:variable name="Labore" select="count(//xt:Labor)"/>
  		<xs:variable name="Rechner" select="count(//xt:Computer)"/>
  		<xs:variable name="Software" select="count(//xt:Software)"/>
  		<!-- local name mit prog3: ersetzen -->
  		<xs:variable name="RechnerproLabor" select="$Rechner div $Labore"/>
  		<tr>
  			<td><xs:value-of select="$Labore"/></td>
  			<td><xs:value-of select="$Rechner"/></td>
  			<td><xs:value-of select="$Software"/></td>
  			<td><xs:value-of select="$RechnerproLabor"/></td>
  			
  		</tr>
  	</table>
  	<xs:call-template name="Inhaltsverzeichnis"></xs:call-template>
  </xs:template>
  <xs:template name="Inhaltsverzeichnis">
  <h1>Inhaltsverzeichnis</h1>
  <ul>
  		<!-- Links überarbeiten, mit mehr Daten testen -->
  		<xs:for-each select="//xt:Labor">
        <li><a href="#{generate-id()}">
        	<xs:value-of select="@Name"/></a></li>
      </xs:for-each>
  		<li><a href="#Software">Software</a></li>
  		<li><a href="#weiteres">Andere geräte</a></li>
  		<li><a href="#Hersteller">Hersteller</a></li>
  </ul>
  		<xs:call-template name="Labordaten"></xs:call-template>
  		<xs:call-template name="Rechnerdaten"></xs:call-template>
  		<xs:call-template name="Softwaredaten"></xs:call-template>
  		<xs:call-template name="Weiteres"></xs:call-template>
  </xs:template>
 <xs:template name="Labordaten">
  <xs:call-template name="Laborerstellen">
    <xs:with-param name="Laborelemente" select="//xt:Labor"/>
  </xs:call-template>
</xs:template>

<xs:template name="Laborerstellen">
  <xs:param name="Laborelemente" select="//xt:Labor"/>
  <xs:choose>
    <xs:when test="$Laborelemente">
      <xs:variable name="currentLabor" select="$Laborelemente[1]"/>
      <div id="{generate-id($currentLabor)}">
        <h2><xs:value-of select="$currentLabor/@Name"/></h2>
        <p>Raum: <xs:value-of select="$currentLabor/@Raum"/></p>
        	<xs:call-template name="Computer">
        	 	<xs:with-param name="Rechner" select="$currentLabor/xt:Computer"/>
        	</xs:call-template>
        	<xs:call-template name="Software">
        	 <xs:with-param name="Software" select="$currentLabor/xt:Software"/></xs:call-template>
        	<ul>
        	<xs:call-template name="etc">
        		 <xs:with-param name="Weiteres" select="$currentLabor/xt:weitere_Geraete"/>
        	 </xs:call-template>
        	 </ul>
      </div>  
      <xs:if test="$Laborelemente">
        <xs:call-template name="Laborerstellen">
          <xs:with-param name="Laborelemente" select="$Laborelemente[position() > 1]"/>
        </xs:call-template>
      </xs:if>
    </xs:when>
  </xs:choose>
</xs:template>
<xs:template name="Software">
<xs:param name="Software" select="$currentLabor/xt:Software">
   </xs:param>
   <xs:variable name="Index" select="position()"/>
	 <xs:choose>
    <xs:when test="$Software">
      <li>
     	 <xs:value-of select="$Software[$Index]/@ID"/><br/>
     	 <xs:value-of select="$Software[$Index]/xt:Softwarename"/><br/>
     	  <xs:value-of select="$Software[$Index]/xt:Softwarebeschreibung"/><br/>
     	   <xs:value-of select="$Software[$Index]/xt:Softwarebesonderheiten"/><br/>
      </li>
      <br/>
      <xs:call-template name="Software">
        <xs:with-param name="Software" select="$Software[position() > $Index]"/>
      </xs:call-template>
    </xs:when>
    <xs:otherwise>
    </xs:otherwise>
  </xs:choose></xs:template>
<xs:template name="Computer">
<xs:param name="Rechner" select="$currentLabor/xt:Computer">
   </xs:param>
   <xs:variable name="Index" select="position()"/>
	 <xs:choose>
    <xs:when test="$Rechner">
      <li>
     	 <xs:value-of select="$Rechner[$Index]/@RechnerId"/><br/>
        <xs:value-of select="$Rechner[$Index]/xt:Betriebssytem"/><br/>
          <xs:value-of select="$Rechner[$Index]/xt:Anschaffung"/><br/>
            <xs:value-of select="$Rechner[$Index]/xt:Erweiterungen/xt:Beschreibung"/><br/>
              <xs:value-of select="$Rechner[$Index]/xt:Rechnerbeschreibung"/><br/>
        <xs:value-of select="$Rechner[$Index]/xt:Rechnerbesonderheiten"/><br/>
        <xs:call-template name="Rechnersoftware">
         <xs:with-param name="Software" select="$Rechner/xt:Software2"/>
        </xs:call-template>
      </li>
      <br/>
      <xs:call-template name="Computer">
        <xs:with-param name="Rechner" select="$Rechner[position() > $Index]"/>
      </xs:call-template>
    </xs:when>
    <xs:otherwise>
    </xs:otherwise>
  </xs:choose>
</xs:template>
<xs:template name="Rechnersoftware">
<xs:param name="Software" select="$Rechner/xt:Software2">
   </xs:param>
   <xs:variable name="Index" select="position()"/>
	 <xs:choose>
    <xs:when test="$Software">
      <li>
     	 <xs:variable name="Liz" select="$Software[$Index]/@Id"/>
     	 <xs:choose>
        <xs:when test="$Software[$Index]/@lizensiert='false'">
        <p><xs:value-of select="$Liz"/>: Nicht lizensiert</p>
        </xs:when>
        <xs:otherwise>
        	<p><xs:value-of select="$Liz"/>: Lizensiert</p>
        </xs:otherwise>
        </xs:choose>
        <br/>
      </li>
      <xs:call-template name="Rechnersoftware">
        <xs:with-param name="Software" select="$Software[position() > $Index]"/>
      </xs:call-template>
    </xs:when>
    <xs:otherwise>
    </xs:otherwise>
    </xs:choose>
</xs:template>
<xs:template name="etc">
 <xs:param name="Weiteres" select="$currentLabor/xt:weitere_Geraete">
   </xs:param>
   <xs:variable name="Index" select="position()"/>
	 <xs:choose>
    <xs:when test="$Weiteres">
      <li>
     	 <xs:value-of select="$Weiteres[$Index]/@WeiteresID"/><br/>
        <xs:value-of select="$Weiteres[$Index]/xt:Art"/><br/>
        <xs:value-of select="$Weiteres[$Index]/xt:Beschreibung"/>
      </li>
      <xs:call-template name="etc">
        <xs:with-param name="Weiteres" select="$Weiteres[position() > $Index]"/>
      </xs:call-template>
    </xs:when>
    <xs:otherwise>
    </xs:otherwise>
  </xs:choose>
</xs:template>
  <xs:template name="Rechnerdaten">
  <h1 id="Software">SoftwareListe</h1>
  <ul>
    <xs:call-template name="ComputerListe">
      <xs:with-param name="currentElements" select="//xt:Softwarename[not(.=preceding::xt:Softwarename)]">
      </xs:with-param>
    </xs:call-template>
  	</ul>
  </xs:template>
  <xs:template name="ComputerListe">
   <xs:param name="currentElements" select="//xt:Softwarename[not(.=preceding::xt:Softwarename)]">
   </xs:param>
   <xs:variable name="Index" select="position()"/>
   
  <xs:choose>
    <xs:when test="$currentElements">
      <li>
        <xs:value-of select="$currentElements[$Index]"/>
      </li>
      <xs:call-template name="ComputerListe">
        <xs:with-param name="currentElements" select="$currentElements[position() > $Index]"/>
      </xs:call-template>
    </xs:when>
    <xs:otherwise>
    </xs:otherwise>
  </xs:choose>
  		   
  </xs:template>
  <xs:template name="Softwaredaten">
  <h1 id="weiteres">Weitere Geräte</h1>
  <ul>
  		<xs:call-template name="Sonstiges">
      <xs:with-param name="currentElements" select="//xt:Art[not(.=preceding::xt.Art)]">
      </xs:with-param>
    </xs:call-template>
  	</ul>
  	</xs:template>
  	<xs:template name="Sonstiges">
  	  <xs:param name="currentElements" select="//xt:Art[not(.=preceding::xt:Art)]">
   </xs:param>
   <xs:variable name="Index" select="position()"/>
   
  <xs:choose>
    <xs:when test="$currentElements">
      <li>
        <xs:value-of select="$currentElements[$Index]"/>
      </li>
      <xs:call-template name="ComputerListe">
        <xs:with-param name="currentElements" select="$currentElements[position() > $Index]"/>
      </xs:call-template>
    </xs:when>
    <xs:otherwise>
    </xs:otherwise>
  </xs:choose>
  	</xs:template>
  <xs:template name="Weiteres">
  <h1 id="Hersteller">Hersteller</h1>
  <ul>
  	 <xs:call-template name="Hersteller">
    <xs:with-param name="Herstellerelemente" select="//xt:hersteller[not(.=preceding::xt:hersteller)]"/>
  </xs:call-template>
  	</ul>
  </xs:template>	
  	<xs:template name="Hersteller">
  			 <xs:param name="Herstellerelemente" select="//xt:hersteller[not(.=preceding::xt:hersteller)]">
   </xs:param>
   <xs:variable name="Index" select="position()"/>
   
  <xs:choose>
    <xs:when test="$Herstellerelemente">
      <li>
        <xs:value-of select="$Herstellerelemente[$Index]"/>
      </li>
      <xs:call-template name="Hersteller">
        <xs:with-param name="Herstellerelemente" select="$Herstellerelemente[position() > $Index]"/>
      </xs:call-template>
    </xs:when>
  </xs:choose>
  	</xs:template>
</xs:stylesheet>