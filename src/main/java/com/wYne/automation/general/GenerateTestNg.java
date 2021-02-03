package com.wYne.automation.general;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.*;

public class GenerateTestNg {

    private Document doc = null;

    /**
     * Method will get test classes and test cases to be added in testng.xml file by calling relative method.
     * Creates testng.xml file by calling relative method.
     */
    public static void main(String args[]) {
        Map<String, List<String>> testClassesAndTestCasesToExecute = new HashMap<>();

        List<String> class1TCs = Arrays.asList("class1Method1", "class1Method2", "class1Method3");
        testClassesAndTestCasesToExecute.put("class1", class1TCs);

        List<String> class2TCs = Arrays.asList("class2Method1");
        testClassesAndTestCasesToExecute.put("class2", class2TCs);

        List<String> class3TCs = null;
        testClassesAndTestCasesToExecute.put("class3", class3TCs);

        List<String> class4TCs = new ArrayList<>();
        testClassesAndTestCasesToExecute.put("class4", class4TCs);

        GenerateTestNg generateTestNg = new GenerateTestNg();
        //generateTestNg.createTestNg(testClassesAndTestCasesToExecute,"priority_all_tests_tests_suite","1029","30",null);
    }

    /**
     * Method will create testng.xml file.
     * passing extra params
     * @param testClassesAndTestCasesToExecute to be added in testng.xml file.
     */
    public void createTestNg(Map<String, List<String>> testClassesAndTestCasesToExecute,String name,
                             String testrun,String parallelType,String threadCount,String filePath,Map<String,String> params){
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "1");
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.newDocument();

            //  Element doctypeElement = doc.createElement("!DOCTYPE suite SYSTEM \"http://testng.org/testng-1.0.dtd\"");

            Element rootElement = doc.createElement("suite"); // (suite) root elements
            rootElement.setAttribute("configfailurepolicy", "continue");
            rootElement.setAttribute("name", name);
            rootElement.setAttribute("parallel", parallelType);
            rootElement.setAttribute("verbose", "1");
            // methods : only data-provider-thread-count will be set, thread-count will be 0
            // classes : only thread-count will be set, data-provider-thread-count will be 0
            if (parallelType.equals("methods")) {
                rootElement.setAttribute("data-provider-thread-count", threadCount);
                //rootElement.setAttribute("thread-count", "1");
            }
            else {
                rootElement.setAttribute("thread-count", threadCount);
                rootElement.setAttribute("data-provider-thread-count", "1");
            }
            doc.appendChild(rootElement);
            setTestNgParam(rootElement, "testrail.testrun",testrun);

            /*Here added params to testng file*/
            if(params!=null && params.size()>0){
                params.entrySet().stream().forEach(val->{
                    setTestNgParam(rootElement, val.getKey(),val.getValue());
                });
            }

            Element testElement = getTestNgTest(rootElement, name+"_test_cases"); //test elements

            Element classes = getTestNgClasses(testElement); //classes elements

            getTestNgClass(classes, testClassesAndTestCasesToExecute);

            DOMSource source = new DOMSource(doc); // write the content into xml file
            StreamResult result =null;

            if(filePath!=null&&filePath!="")
                result =new StreamResult(new File(filePath));
            else
                result = new StreamResult(new File("testng.xml"));

            transformer.transform(source, result);
            //System.out.println("File saved!");
        } catch (TransformerException | ParserConfigurationException tfe) {
            tfe.printStackTrace();
        }
    }

    /**
     * Method will create parameter tag.
     *
     * @param rootElement parent of parameter tag.
     * @param paramName   parameter tag name.
     * @param paramValue  parameter tag value.
     */
    public void setTestNgParam(Element rootElement, String paramName, String paramValue) {
        Element parameter = doc.createElement("parameter"); // parameter elements
        rootElement.appendChild(parameter);
        parameter.setAttribute("name", paramName); // set attribute to parameter element
        parameter.setAttribute("value", paramValue);
    }

    /**
     * Method will create test tag.
     *
     * @param rootElement parent of test tag.
     * @param testName    test tag name.
     * @return test tag.
     */
    public Element getTestNgTest(Element rootElement, String testName) {
        Element test = doc.createElement("test");
        rootElement.appendChild(test);
        test.setAttribute("name", testName);
        return test;
    }

    /**
     * Method will create classes tag.
     *
     * @param testElement parent of classes tag.
     * @return classes tag.
     */
    public Element getTestNgClasses(Element testElement) {
        Element classes = doc.createElement("classes");
        testElement.appendChild(classes);
        return classes;
    }

    /**
     * Method will create class, method and include tags.
     *
     * @param classes                          parent of class tag.
     * @param testClassesAndTestCasesToExecute to be added in class , method and include tags.
     */
    public void getTestNgClass(Element classes, Map<String, List<String>> testClassesAndTestCasesToExecute) {
        Set<String> testClassSet = testClassesAndTestCasesToExecute.keySet();

        for (String className : testClassSet) {
            Element classTag = doc.createElement("class");
            classes.appendChild(classTag);
            classTag.setAttribute("name", className);

            List<String> testCaseList = testClassesAndTestCasesToExecute.get(className);
            if ((testCaseList != null) && (!testCaseList.isEmpty())) {
                Element methods = doc.createElement("methods"); //method elements
                classTag.appendChild(methods);
                for (String testCaseName : testCaseList) {
                    Element include = doc.createElement("include"); //include elements
                    methods.appendChild(include);
                    include.setAttribute("name", testCaseName);
                }
            }
        }
    }
}
