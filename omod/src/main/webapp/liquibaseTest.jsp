<%@ page import="java.text.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.openmrs.api.context.*" %>
<%@ page import="org.openmrs.module.cpm.*" %>
<%@ page import="org.openmrs.module.cpm.api.*" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Testing Liquibase</title>
</head>
<body>
<%
	// The following code is responsible for cycling through some basic operations to confirm that the 
	// database as created by the liquibase.xml file is aligned with the .hbm.xml file.  This does not
	// functionally test the code - as this is already done by the unit tests
	
	final String CPM_CORE_DATASET = "org/openmrs/module/cpm/coreTestData.xml";
	final String TEST_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
	final String TEST_DATE_DISPLAY_FORMAT = "yyyy-MM-dd HH:mm:ss.S z G";
	
	ProposedConceptService service = Context.getService(ProposedConceptService.class);
	SimpleDateFormat formatter = new SimpleDateFormat(TEST_DATE_FORMAT);
	SimpleDateFormat comparator = new SimpleDateFormat(TEST_DATE_DISPLAY_FORMAT);

	List<ProposedConceptPackage> packages;
	List<ProposedConceptResponsePackage> responsePackages;
	ProposedConceptPackage conceptPackage;
	ProposedConceptResponsePackage conceptResponsePackage;
	ProposedConceptPackage testPackage = null;
	ProposedConceptResponsePackage testResponsePackage = null;

	service = Context.getService(ProposedConceptService.class);

	conceptPackage = new ProposedConceptPackage();
	conceptPackage.setId(0);
	conceptPackage.setName("name");
	conceptPackage.setDescription("description");
	conceptPackage.setEmail("test@test.com");
	conceptPackage.setVersion(0);

	conceptResponsePackage = new ProposedConceptResponsePackage(conceptPackage);
	conceptResponsePackage.setId(0);
	conceptResponsePackage.setName("description");
	conceptResponsePackage.setVersion(0);

	// Start by saving a pckage
	
	service.saveProposedConceptPackage(conceptPackage);
	
	// Then retrieve all the existing packages
	packages = service.getAllProposedConceptPackages();

	// Then retrieve it by ID
	
	testPackage = service.getProposedConceptPackageById(1);

	// Then retrieve it by UUID
	
	testPackage = service.getProposedConceptPackageById(null);
	
	// Then delete it
	
	service.deleteProposedConceptPackage(testPackage);
	    
	
	
	// Start by saving a pckage
	
	service.saveProposedConceptResponsePackage(conceptResponsePackage);
	
	// Then retrieve all the existing packages
	
	responsePackages = service.getAllProposedConceptResponsePackages();

	// Then retrieve it by ID
	
	testResponsePackage = service.getProposedConceptResponsePackageById(1);

	// Then retrieve it by UUID
	
	testResponsePackage = service.getProposedConceptResponsePackageById(null);
	
	// Then delete it
	
	service.deleteProposedConceptResponsePackage(testResponsePackage);
%>
</body>
</html>