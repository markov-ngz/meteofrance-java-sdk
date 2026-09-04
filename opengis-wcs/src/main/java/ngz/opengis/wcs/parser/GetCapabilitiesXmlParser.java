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
import ngz.opengis.wcs.dto.GetCapabilities;
import ngz.opengis.wcs.exception.WcsParsingException;
import ngz.opengis.wcs.exception.WcsValidationException;

/** Interface for parsing WCS GetCapabilities XML responses. */
public class GetCapabilitiesXmlParser implements WcsResponseParser<GetCapabilities.Capabilities> {

    /** Loading JAXBContext within its own thread with plugin loader instead of runtime. */
    private JAXBContext loadJAXBContext() throws JAXBException {
        ClassLoader original = Thread.currentThread().getContextClassLoader();

        try {
            Thread.currentThread().setContextClassLoader(getClass().getClassLoader());

            return JAXBContext.newInstance(GetCapabilities.Capabilities.class);

        } finally {
            Thread.currentThread().setContextClassLoader(original);
        }
    }

    /**
     * Parses GetCapabilities XML and returns the Capabilities DTO.
     *
     * @param xmlBytes the GetCapabilities XML content as byte array
     * @return the parsed Capabilities DTO
     */
    @Override
    public GetCapabilities.Capabilities parse(byte[] xmlBytes)
            throws WcsParsingException, WcsValidationException {
        if (xmlBytes == null || xmlBytes.length == 0) {
            throw new WcsParsingException("XML content is null or empty");
        }

        try {

            JAXBContext context = loadJAXBContext();

            // 2. Create the unmarshaller
            Unmarshaller unmarshaller = context.createUnmarshaller();

            // 3. Unmarshal the XML into the Java Object
            GetCapabilities.Capabilities response =
                    (GetCapabilities.Capabilities)
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
    private void validateParsedResult(GetCapabilities.Capabilities result)
            throws WcsValidationException {
        // 2. Setup Validator
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // 3. Check for errors
        Set<ConstraintViolation<GetCapabilities.Capabilities>> violations =
                validator.validate(result);

        if (!violations.isEmpty()) {
            for (ConstraintViolation<GetCapabilities.Capabilities> violation : violations) {
                System.out.println(violation.getPropertyPath() + " " + violation.getMessage());
            }
            throw new WcsValidationException("XML Validation failed!");
        }
    }
}
