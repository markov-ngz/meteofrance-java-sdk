package ngz.opengis.wcs.parser;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.util.Set;
import ngz.opengis.wcs.dto.DescribeCoverage;
import ngz.opengis.wcs.exception.WcsException;
import ngz.opengis.wcs.exception.WcsParsingException;
import ngz.opengis.wcs.exception.WcsValidationException;

/** Parser for WCS DescribeCoverage XML responses. */
public class CoverageDescriptionXmlParser
        implements WcsResponseParser<DescribeCoverage.CoverageDescriptions> {

    /** Loading JAXBContext within its own thread with plugin loader instead of runtime. */
    private JAXBContext loadJAXBContext() throws JAXBException {
        ClassLoader original = Thread.currentThread().getContextClassLoader();

        try {
            Thread.currentThread().setContextClassLoader(getClass().getClassLoader());

            return JAXBContext.newInstance(DescribeCoverage.CoverageDescriptions.class);

        } finally {
            Thread.currentThread().setContextClassLoader(original);
        }
    }

    /**
     * Parses DescribeCoverage XML and returns the CoverageDescriptionsXml DTO.
     *
     * @param xmlBytes the DescribeCoverage XML content as byte array
     * @return the parsed CoverageDescriptionsXml DTO
     * @throws WcsXmlValidationException if the XML content is null or empty
     * @throws WcsParsingException if parsing fails due to malformed or invalid XML
     */
    @Override
    public DescribeCoverage.CoverageDescriptions parse(byte[] xmlBytes) throws WcsException {

        if (xmlBytes == null || xmlBytes.length == 0) {
            throw new WcsParsingException("XML content is null or empty");
        }

        try {

            System.out.println("TCCL = " + Thread.currentThread().getContextClassLoader());

            System.out.println("Parser classloader = " + getClass().getClassLoader());
            // 1. Create the context scoped to your root class
            JAXBContext context = loadJAXBContext();

            // 2. Create the unmarshaller
            Unmarshaller unmarshaller = context.createUnmarshaller();

            // 3. Unmarshal the XML into the Java Object
            DescribeCoverage.CoverageDescriptions response =
                    (DescribeCoverage.CoverageDescriptions)
                            unmarshaller.unmarshal(new ByteArrayInputStream(xmlBytes));

            validateParsedResult(response);
            return response;

        } catch (JAXBException e) {
            throw new WcsParsingException("Failed to parse XML: " + e.getMessage(), e);
        }
    }

    /**
     * Validates the parsed CoverageDescriptionsXml result.
     *
     * @param result the parsed result to validate
     * @throws WcsValidationException if validation fails
     */
    private void validateParsedResult(DescribeCoverage.CoverageDescriptions result)
            throws WcsValidationException {
        // 2. Setup Validator
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // 3. Check for errors
        Set<ConstraintViolation<DescribeCoverage.CoverageDescriptions>> violations =
                validator.validate(result);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<DescribeCoverage.CoverageDescriptions> violation :
                    violations) {
                System.out.println(violation.getPropertyPath() + " " + violation.getMessage());
            }
            throw new WcsValidationException("XML Validation failed!");
        }
    }
}
