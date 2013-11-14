# USPS Module

Rather than use banded shipping estimation, some users will prefer to use the built-in support for USPS shipping calculation. Broadleaf Commerce utilizes an object-based representation of the USPS XML API for shipping rate calculation processed through a custom communication infrastructure. We have made every effort to faithfully represent the USPS XML API and feature set in our model. Before proceeding, please review the following prerequisites:

- Users must establish their own account with USPS in order to use the Broadleaf Commerce USPS functionality (register at https://www.usps.com/business/webtools.htm)
- Review the Development Guide for getting started steps (https://www.usps.com/business/webtools-technical-guides.htm)
- Review the Rates-Calculators documentation on the USPS site (https://www.usps.com/business/webtools-technical-guides.htm)

Once you have established an account with USPS and made yourself familiar with the API features and requirements, you should begin to setup your environment to work with Broadleaf Commerce USPS support. The first step is to make Broadleaf Commerce aware of your USPS account credentials. This is accomplished through environment configuration (see [[Runtime Environment Configuration]]).

Specifically, you will need to add, at a minimum, the following properties to your application. Likely, you will want to add them to `common-shared.properties` in the core project

```text
usps.password=[my USPS provided password]
usps.user.name=[my USPS provided username]
```

Additionally, you'll need to configure the Spring bean that's responsible for instantiating the USPSShippingCalculationService instance. This service is the base bean responsible for communicating with the USPS web service API. Add the following bean configuration to your application context:

```xml
<bean id="blShippingCalculationService" class="org.broadleafcommerce.vendor.usps.service.USPSShippingCalculationServiceImpl">
    <property name="uspsCharSet" value="${usps.charset}"/>
    <property name="uspsPassword" value="${usps.password}"/>
    <property name="uspsServerName" value="${usps.server.name}"/>
    <property name="uspsServiceAPI" value="${usps.service.api}"/>
    <property name="uspsUserName" value="${usps.user.name}"/>
    <property name="failureReportingThreshold" value="10"/>
    <property name="httpProtocol" value="${usps.http.protocol}"/>
    <property name="uspsShippingAPI" value="${usps.shipping.api}"/>
    <property name="rateRequestElement" value="${usps.rate.request.tag}"/>
    <property name="uspsRequestValidator">
        <bean class="org.broadleafcommerce.vendor.usps.service.message.USPSRequestValidator">
            <constructor-arg>
                <bean class="org.broadleafcommerce.common.util.EnvironmentFactoryBean">
                    <constructor-arg value="${usps.rate.request.validator}"/>
                </bean>
            </constructor-arg>
        </bean>
    </property>
    <property name="uspsRequestBuilder">
        <bean class="org.broadleafcommerce.common.util.EnvironmentFactoryBean">
            <constructor-arg value="${usps.rate.request.builder}"/>
        </bean>
    </property>
    <property name="uspsResponseBuilder">
        <bean class="org.broadleafcommerce.common.util.EnvironmentFactoryBean">
            <constructor-arg value="${usps.rate.response.builder}"/>
        </bean>
    </property>
</bean>
```

All of the property values specified here are coming from the BroadleafCommerce configuration for USPS except for your username and password. If at any point you need to change these configuration values, you can override in your property files in your own environment configuration. Currently, Broadleaf specifies the RateV2 API for development and integration and the RateV3 API for production and staging for the usps.shipping.api property. This is in line with USPS setup, but you may override these values in your own configuration if desired.

Now that your user credentials are available to Broadleaf Commerce, you are essentially ready to start interacting with the USPS shipping API servers. The environment type you're launching under (development, staging, integration, production) determines whether or not you will be hitting the USPS test or production servers. Development and Integration both target the USPS test environment. Staging and Production both target the USPS production environment.

At this point, you should consider writing an integration test similar to the [USPSShippingServiceTest](https://github.com/BroadleafCommerce/BroadleafCommerceThirdPartyIntegrationModules/blob/master/integration/src/test/java/org/broadleafcommerce/vendor/USPSShippingServiceTest.java) class in the Broadleaf Commerce codebase. This will allow you to confirm your configuration is functional and will also help you pass the "canned" testing requirements with USPS.

Once USPS has provided you server access to work with live data, then you are ready to integrate with USPSShippingCalculationModule. USPSShippingCalculationModule is the integration point between the BroadleafCommerce core and the USPSShippingCalculationService. USPSShippingCalculationModule is itself and abstract class that performs much of the work of driving the shipping pricing request communication, but is not capable of determining how to package the items in an order together in boxes in order to make the USPS shipping pricing request. To be able to complete a real shipping pricing request from BroadleafCommerce core, you will need to either choose a BroadleafCommerce extension of USPSShippingCalculationModule, or develop your own custom extension.

### Use the USPSSingleItemPerPackageShippingCalculationModule implementation

This BroadleafCommerce shipping calculation module simply uses the dimension and weight information for each product in the order and assumes that each item in the order will be packaged and shipped separately. This will generally not be the most useful approach, as more than one item will generally be able to be included in a single shipping box. Developers may wish to extend USPSShippingCalculationModule with their own custom implementation, or utilize the BandedShippingModule for estimated shipping. To use this module, you'll need to add the following bean declaration to your application context:

```xml
<bean id="blShippingModule" class="org.broadleafcommerce.pricing.service.module.USPSSingleItemPerPackageShippingCalculationModule">
    <property name="defaultModule" value="true"/>
    <property name="originationPostalCode" value="10022"/>
</bean>
```

Because this bean uses the BroadleafCommerce key ID for shipping module (`blShippingModule`), it will override the default shipping module (banded shipping) with your instance of USPSSingleItemPerPackageShippingCalculationModule (see [Spring Bean Extension Quick Reference]). There are two parameters of note for this declaration:

- defaultModule - the ShippingModule interface provides several methods for determining if a module is appropriate for a given shipping method. These methods include the isValidModuleForService and isDefaultModule methods. The USPSShippingCalculationModule will first check the service property on the fulfillment group to make sure the method of service is USPS. Because of this condition, it is possible to declare several shipping pricing modules with BroadleafCommerce for different shipping providers. If you are supporting more than one shipping provider, then you should not set the defaultModule property on your USPSSingleItemPerPackageShippingCalculationModule instance at all, or set it to false. However, if you are only supporting USPS for shipping pricing, then setting defaultModule to true will cause your module to be used for pricing, regardless of the service specified on the fulfillment group (in fact, the service value on the fulfillment group can be null in this case).
- originationPostalCode - this value is the postal code from which items will be shipped. This is likely the postal code of your warehouse location, or wherever USPS will be picking up your customer orders for shipment to their various destinations.

## Create a custom extension of USPSShippingCalculationModule

Using the USPSSingleItemPerPackageShippingCalculationModule may not meet your needs and you may find it necessary to develop your own extension of USPSShippingCalculationModule to perform more complex packaging arrangements for your shipped products. Custom modules must implement the createPackages method from USPSShippingCalculationModule:

```java
protected abstract List<USPSContainerItemRequest> createPackages(FulfillmentGroup fulfillmentGroup) throws ShippingPriceException;
```

In your implementation, use whatever logic is necessary to properly package your order items into a list of USPSContainerItemRequest instances. Since this method provides the fulfillmentGroup, you have API access to the fulfillmentGroupItems, which will yield the DiscreteOrderItems and product dimension and weight information necessary to make your packaging determinations. Please review the source code for USPSSingleItemPerPackageShippingCalculationModule for an example of how to use the FulfillmentGroup API to determine these values and construct the list of USPSContainerItemRequest instances.

Declare an instance of your custom shipping pricing module in your application context making sure to specify `blShippingModule` as the bean id in order to override the default BroadleafCommerce shipping pricing module.

## FulfillmentGroup Requirements

For the USPS requests to be properly created, several pieces of information are required on the FulfillmentGroup instance (in addition to the product dimension and weight information for each order item).

- FulfillmentGroup.service - this property is used by BroadleafCommerce to determine if your module is appropriate for the shipping service specified for the fulfillment group. This property should always be filled in unless you are using only one shipping provider and your have declared defaultModule property as true on your shipping calculation module. The values available out-of-the-box are defined in the ShippingServiceType enumeration.

- FulfillmentGroup.method - this property is used to identify the type of service from the given shipping vendor (first class, etc...). The various values are defined in the USPSServiceMethod enumeration. Please note, for first class service, it may be necessary to include a secondary method that identifies the type of first class service. The various values for this secondary method are defined in the USPSFirstClassType enumeration. In the case of first class, the FulfillmentGroup.method property should be defined as a string whose primary and secondary methods are separated by an underscore character. For example, to create an appropriate method for first class parcel, you would create a string as follows: `USPSServiceMethod.FIRSTCLASS + "_" + USPSFirstClassType.PARCEL`.

