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
                <xs:call-template name="Inhaltsverzeichnis"/>
                <xs:apply-template select="//xt:Labor"/>

                <xs:call-template name="Softwaredaten"> </xs:call-template>
                <xs:call-template name="weitere_Geraete"> </xs:call-template>
                <xs:call-template name="Hersteller"> </xs:call-template>
                <xs:call-template name="generateTable"> </xs:call-template>
            </body>
        </html>
    </xs:template>

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
                        Rechner mit Lizenzierter Software
                    </th>
                    <th>
                        Einzigartige Software
                    </th>
                    <th>
                        Anzahl der Lizenzen
                    </th>
                </tr>
        <xs:variable name="Labore" select="count(//xt:Labor)"/>
        <xs:variable name="Rechner" select="count(//xt:Computer)"/>
        <xs:variable name="Software" select="count(//xt:Software)"/>
        <xs:variable name="Softwareunique" select="count(//xt:Software[.=preceding::xt:Software])"/>
        <xs:variable name="Uniquesoft" select="count(//xs:SOftware2[@lizenziert='false'])"/>
        <xs:variable name="Soft2" select="count(//xt:Software2)"/>
        <xs:variable name="Lizensen" select="$Soft2 - $Uniquesoft"/>
        <xs:variable name="RechnerproLabor" select="$Rechner div $Labore"/>
        <xs:variable name="Rechnersoft" select="count(//xt:Computer/xt:Software2[@lizenziert='true'])"/>
        <xs:variable name="Lizenzen" select="count(.)"/>
            <tr>
                <td><xs:value-of select="$Labore"></td>
                <td><xs:value-of select="$Rechner"/></td>
                <td><xs:value-of select="$Software"/></td>
                <td><xs:value-of select="$RechnerproLabor"/></td>
                <td><xs:value-of select="$Rechnersoft"/></td>
                <td><xs:value-of select="$Softwareunique"/></td>
                <td><xs:value-of select="$Lizensen"/></td>
            </tr>
        </table>
    </xs:template>
    <xs:template name="Inhaltsverzeichnis">
        <h1>Inhaltsverzeichnis</h1>
        <ul>
        <xs:for-each select="//xt:Labor">
            <li>
                <a href="#{generate-id()}">
                    <xs:value-of select="@Name"/>
                </a>
            </li>
        </xs:for-each>
        <li><a href="#Software">Software</a></li>
        <li><a href="#weiteres">Andere Geräte</a></li>
        <li><a href="#Hersteller">Hersteller</a></li>
        <li><a href="#Tabelle">Tabelle</a></li>
        </ul>
    </xs:template>
    <xs:template match="xt:Labor">
        <div id="{generate-id()}">
            <h2><xs:value-of select="@Name"/></h2>
            <h2>Raum: <xs:value-of select="@Raum"></h2>
            <h2>Rechner:</h2>
            <xs:apply-templates select="xt:Computer"/>
            <br/>
            <h2>Software:</h2>
            <xs:apply-templates select="xt:weitere_Geraete"/>
            <br/>
            <h2>Weitere Geräte:</h2>
            <xs:apply-templates select="xt:weitere_Geraete"/>
        </div>
    </xt:template>
    <xs:template match="xt:Software">
        <h2>SoftwareID:<xs:value-of select="@ID"/></h2>
        <p>Softwarename:<xs:value-of select="xs:Softwarename"/></p>
        <p>Beschreibung:<xs:value-of select="xt:Softwarebeschreibung"/></p>
        <p>Besonderheiten:<xs:value-of select="xt:Softwarebesonderheiten"/></p>
    </xs:template>
    <xs:template match="xt:Computer">
        <li>
            <h2>RechnerID:<xs:value-of select="@RechnerId"></h2>
            <p>Betriebssystem:<xs:value-of select="xt:Betriebssystem"/></p>
            <p>Anschaffungsdatum:<xs:value-of select="xt:Anschaffung"/></p>
            <p>Erweiterungen:<xs:value-of select="xt:Erweiterungen/xt:Beschreibung"/></p>
            <p>Besonderheiten:<xs:value-of select="xt:Rechnerbesonderheiten"/></p>
            <xs:apply-templates select="xt:Software2">
                    <xs:sort select="@Id"/>

            </xs:apply-templates>
        </li>
    </xs:template>

    <xs:template match="xt:Software2">
        <li>
        <xs:variable name="Liz" select="@Id"/>
        <xs:choose>
            <xs:when test="@lizensiert='false'">
                    <p>Software: <xs:value-of select="$Liz"/> (Lizensiert)</p>
            </xs:when>
            <xs:otherwise>
                <p>Software: <xs:value-of select="$Liz"/> (Lizensiert)</p>
            </xs:otherwise>
        </xs:choose>
        </li>
    </xt:template>
    <xs:template match="xt:weitere_Geraete">
        <li>
        <p>ID: <xs:value-of select="@WeiteresID"/></p>
        <p>Art: <xs:value-of select="xt:Art"/></p>
        <p>Beschreibung: <xs:vlaue-of select="xt:Beschreibung"/></p>
        </li>
    </xs:template>

    <xt:template name="Softwaredaten">
        <h1 id="Software">SoftwareListe</h1>
        <ul>
        <xs:apply-templates select="//xt:Softwarename[not(.=preceding::xt:Softwarename)]">
            <xs:sort order="ascending"/>
        </xs:apply-templates>
        </ul>
    </xs:template>

    <xt:template match="xt:Softwarename">

</xs:stylesheet>