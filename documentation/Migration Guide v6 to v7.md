# Migrating from version 6.x.x to 7.x.x

## Dependency
The `groupId` of the SDK has changed to `com.worldline-solutions.connect`, which means you need to update your Gradle dependency.

In your `build.gradle` replace `com.ingenico.connect.gateway` with `com.worldline.connect`.

## Deprecated
The following classes have been deprecated:
- `PaymentItemCacheKey`
- `Size`
- `GooglePayUtil`

The following function has been deprecated:
- `applyMask(String, String, Integer)` in `PaymentProductField`

## Removed
The following classes have been removed:
- Classes used for image storing: `AssetManager``CacheHandler`, `Preferences`, `ReadInternalStorage` and `WriteInternalStorage`
- Unused exceptions: `BadPaymentItemException`, `CommunicationException` and `UnknownNetworkException`
- Classes of previous SDK architecture: `C2SCommunicator`, `C2SCommunicatorConfiguration`, `Session` and `SessionEncryptionHelper`
- Async tasks: `BasicPaymentItemsAsyncTask`, `BasicPaymentProductGroupsAsyncTask`, `BasicPaymentProductsAsyncTask`, `ConvertAmountAsyncTask`,
    `CustomerDetailsAsyncTask`, `IinLookupAsyncTask`, `LoadImageAsyncTask`, `PaymentProductAsyncTask`, `PaymentProductDirectoryAsyncTask`,
    `PaymentProductGroupAsyncTask`, `PublicKeyAsyncTask` and `ThirdPartyStatusAsyncTask`
- `PreparedPaymentRequest`
- `TLSSocketFactory`
- `Util`


The following functions have been removed:
- `isGooglePayAllowed()` function in `GooglePayUtil`
- `getImage()` in `Tooltip`

The following properties have been removed:
- Constant properties from `Constants`: `ACCEPTABLE_WAIT_TIME_IN_MILISECONDS`, `DIRECTORY_IINRESPONSES`, `DIRECTORY_LOGOS`, `ENABLE_REQUEST_LOGGING`,
    `FILENAME_IINRESPONSE_CACHE`, `FILENAME_LOGO_PREFIX`, `GC_GATEWAY_RETRIEVE_PAYMENTPRODUCTS_PATH`, `GC_GATEWAY_RETRIEVE_PAYMENTPRODUCT_PATH`,
    `GC_GATEWAY_RETRIEVE_PAYMENTPRODUCT_DIRECTORY_PATH`, `GC_GATEWAY_RETRIEVE_PAYMENTPRODUCT_NETWORKS_PATH`,
    `GC_GATEWAY_RETRIEVE_PAYMENTPRODUCTGROUPS_PATH`, `GC_GATEWAY_RETRIEVE_PAYMENTPRODUCTGROUP_PATH`, `GC_GATEWAY_CONVERT_AMOUNT_PATH`,
    `GC_GATEWAY_IIN_LOOKUP_PATH`, `GC_GATEWAY_PUBLIC_KEY_PATH`, `GC_GATEWAY_THIRDPARTYSTATUS_PATH`, `INSTALLMENTPLAN_FIELD_ID`,
    `PAYMENTPRODUCTID_BCMC`,`PAYMENTPRODUCTID_AFTERPAY_INSTALLMENTS`, `PAYMENTPRODUCTID_AFTERPAY_INVOICE`, `PREFERENCES_NAME`,
    `PREFERENCES_LOGO_MAP` and `TERMSANDCONDITIONS_FIELD_ID`

The following classes can no longer be accessed outside the SDK:
- `EncryptData`, `EncryptUtil` and `Encryptor`

## Imports
All package names have been renamed, and some classes have been moved to different packages. 

You need to change your imports as follows:

| Previous package                                                                      | Class / Enum / Interface                            | New package                                   
|---------------------------------------------------------------------------------------|-----------------------------------------------------|------------------------------------------------------------------------
| com.ingenico.connect.gateway.sdk.client.android                                       | ConnectSDK                                          | com.worldline.connect.sdk.client.android
| com.ingenico.connect.gateway.sdk.client.android.sdk                                   | ClientApi                                           | com.worldline.connect.sdk.client.android
| com.ingenico.connect.gateway.sdk.client.android.sdk.configuration                     | ConnectSDKConfiguration                             | com.worldline.connect.sdk.client.android.configuration      
| com.ingenico.connect.gateway.sdk.client.android.sdk.configuration                     | PaymentConfiguration                                | com.worldline.connect.sdk.client.android.configuration 
| com.ingenico.connect.gateway.sdk.client.android.sdk.configuration                     | SessionConfiguration                                | com.worldline.connect.sdk.client.android.configuration    
| com.ingenico.connect.gateway.sdk.client.android.sdk.configuration                     | Constants                                           | com.worldline.connect.sdk.client.android.constants     
| com.ingenico.connect.gateway.sdk.client.android.sdk.exception                         | EncryptDataException                                | com.worldline.connect.sdk.client.android.exception    
| com.ingenico.connect.gateway.sdk.client.android.sdk.model                             | FormatResult                                        | com.worldline.connect.sdk.client.android.formatter
| com.ingenico.connect.gateway.sdk.client.android.sdk.formatter                         | StringFormatter                                     | com.worldline.connect.sdk.client.android.formatter
| com.ingenico.connect.gateway.sdk.client.android.sdk                                   | GooglePayUtil                                       | com.worldline.connect.sdk.client.android.googlepay
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.paymentproduct              | KeyValuePair                                        | com.worldline.connect.sdk.client.android.model.accountonfile.attributes
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.paymentproduct              | AccountOnFileDisplay                                | com.worldline.connect.sdk.client.android.model.accountonfile.displayhints
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.paymentproduct.displayhints | DisplayHintsAccountOnFile                           | com.worldline.connect.sdk.client.android.model.accountonfile.displayhints
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.paymentproduct              | AccountOnFile                                       | com.worldline.connect.sdk.client.android.model.accountonfile
| com.ingenico.connect.gateway.sdk.client.android.sdk.model                             | ConvertedAmountResponse                             | com.worldline.connect.sdk.client.android.model.convertamount
| com.ingenico.connect.gateway.sdk.client.android.sdk.model                             | DirectoryEntry                                      | com.worldline.connect.sdk.client.android.model.directoryentry
| com.ingenico.connect.gateway.sdk.client.android.sdk.model                             | PaymentProductDirectoryResponse                     | com.worldline.connect.sdk.client.android.model.directoryentry
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.iin                         | IinDetail                                           | com.worldline.connect.sdk.client.android.model.iindetails
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.iin                         | IinDetailsRequest                                   | com.worldline.connect.sdk.client.android.model.iindetails
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.iin                         | IinDetailsResponse                                  | com.worldline.connect.sdk.client.android.model.iindetails
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.iin                         | IinStatus                                           | com.worldline.connect.sdk.client.android.model.iindetails
| com.ingenico.connect.gateway.sdk.client.android.sdk.model                             | AmountOfMoney                                       | com.worldline.connect.sdk.client.android.model.paymentcontext
| com.ingenico.connect.gateway.sdk.client.android.sdk.model                             | PaymentContext                                      | com.worldline.connect.sdk.client.android.model.paymentcontext
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.paymentproduct              | BasicPaymentItem                                    | com.worldline.connect.sdk.client.android.model.paymentitem
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.paymentproduct              | BasicPaymentItems                                   | com.worldline.connect.sdk.client.android.model.paymentitem
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.paymentproduct.displayHints | DisplayHintsPaymentItem                             | com.worldline.connect.sdk.client.android.model.paymentitem
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.paymentproduct              | PaymentItem                                         | com.worldline.connect.sdk.client.android.model.paymentitem
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.paymentproduct.displayHints | DisplayHintsProductFields                           | com.worldline.connect.sdk.client.android.model.paymentproduct.field.displayhints
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.paymentproduct              | FormElement                                         | com.worldline.connect.sdk.client.android.model.paymentproduct.field.displayhints
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.paymentproduct              | PaymentProductFieldDisplayElement                   | com.worldline.connect.sdk.client.android.model.paymentproduct.field.displayhints
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.paymentproduct              | Tooltip                                             | com.worldline.connect.sdk.client.android.model.paymentproduct.field.displayhints
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.paymentproduct              | ValueMap                                            | com.worldline.connect.sdk.client.android.model.paymentproduct.field.displayhints
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.paymentproduct              | DataRestrictions                                    | com.worldline.connect.sdk.client.android.model.paymentproduct.field
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.paymentproduct              | PaymentProductField                                 | com.worldline.connect.sdk.client.android.model.paymentproduct.field
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.paymentproduct.specificdata | PaymentProduct302SpecificData                       | com.worldline.connect.sdk.client.android.model.paymentproduct.specificdata
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.paymentproduct.specificdata | PaymentProduct320SpecificData                       | com.worldline.connect.sdk.client.android.model.paymentproduct.specificdata
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.paymentproduct.specificdata | PaymentProduct863SpecificData                       | com.worldline.connect.sdk.client.android.model.paymentproduct.specificdata
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.paymentproduct.validation   | BoletoBancarioRequiredness                          | com.worldline.connect.sdk.client.android.model.paymentproduct.validation
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.paymentproduct.validation   | EmailAddress                                        | com.worldline.connect.sdk.client.android.model.paymentproduct.validation
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.paymentproduct.validation   | ExpirationDate                                      | com.worldline.connect.sdk.client.android.model.paymentproduct.validation
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.paymentproduct.validation   | FixedList                                           | com.worldline.connect.sdk.client.android.model.paymentproduct.validation
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.paymentproduct.validation   | IBAN                                                | com.worldline.connect.sdk.client.android.model.paymentproduct.validation
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.paymentproduct.validation   | Length                                              | com.worldline.connect.sdk.client.android.model.paymentproduct.validation
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.paymentproduct.validation   | Luhn                                                | com.worldline.connect.sdk.client.android.model.paymentproduct.validation
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.paymentproduct.validation   | Range                                               | com.worldline.connect.sdk.client.android.model.paymentproduct.validation
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.paymentproduct.validation   | RegularExpression                                   | com.worldline.connect.sdk.client.android.model.paymentproduct.validation
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.paymentproduct.validation   | ResidentIdNumber                                    | com.worldline.connect.sdk.client.android.model.paymentproduct.validation
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.paymentproduct.validation   | TermsAndConditions                                  | com.worldline.connect.sdk.client.android.model.paymentproduct.validation
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.paymentproduct.validation   | Validator                                           | com.worldline.connect.sdk.client.android.model.paymentproduct.validation
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.paymentproduct              | AuthenticationIndicator                             | com.worldline.connect.sdk.client.android.model.paymentproduct
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.paymentproduct              | BasicPaymentProduct                                 | com.worldline.connect.sdk.client.android.model.paymentproduct
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.paymentproduct              | BasicPaymentProducts                                | com.worldline.connect.sdk.client.android.model.paymentproduct
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.paymentproduct              | MobileIntegrationLevel                              | com.worldline.connect.sdk.client.android.model.paymentproduct
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.paymentproduct              | PaymentProduct                                      | com.worldline.connect.sdk.client.android.model.paymentproduct
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.paymentproduct              | BasicPaymentProductGroup                            | com.worldline.connect.sdk.client.android.model.paymentproductgroup
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.paymentproduct              | BasicPaymentProductGroups                           | com.worldline.connect.sdk.client.android.model.paymentproductgroup
| com.ingenico.connect.gateway.sdk.client.android.sdk.model.paymentproduct              | PaymentProductGroup                                 | com.worldline.connect.sdk.client.android.model.paymentproductgroup
| com.ingenico.connect.gateway.sdk.client.android.sdk.model                             | EncryptedPaymentRequest                             | com.worldline.connect.sdk.client.android.model.paymentrequest
| com.ingenico.connect.gateway.sdk.client.android.sdk.model                             | PaymentRequest                                      | com.worldline.connect.sdk.client.android.model.paymentrequest
| com.ingenico.connect.gateway.sdk.client.android.sdk.model                             | PublicKeyResponse                                   | com.worldline.connect.sdk.client.android.model.publickey
| com.ingenico.connect.gateway.sdk.client.android.sdk.model                             | ThirdPartyStatus                                    | com.worldline.connect.sdk.client.android.model.thirdpartystatus
| com.ingenico.connect.gateway.sdk.client.android.sdk.model                             | ThirdPartyStatusResponse                            | com.worldline.connect.sdk.client.android.model.thirdpartystatus
| com.ingenico.connect.gateway.sdk.client.android.sdk.validation                        | AbstractValidationRule                              | com.worldline.connect.sdk.client.android.model.validation
| com.ingenico.connect.gateway.sdk.client.android.sdk.validation                        | ValidationErrorMessage                              | com.worldline.connect.sdk.client.android.model.validation
| com.ingenico.connect.gateway.sdk.client.android.sdk.validation                        | ValidationRule                                      | com.worldline.connect.sdk.client.android.model.validation
| com.ingenico.connect.gateway.sdk.client.android.sdk.validation                        | ValidationRuleBoletoBancarioRequiredness            | com.worldline.connect.sdk.client.android.model.validation
| com.ingenico.connect.gateway.sdk.client.android.sdk.validation                        | ValidationRuleEmailAddress                          | com.worldline.connect.sdk.client.android.model.validation
| com.ingenico.connect.gateway.sdk.client.android.sdk.validation                        | ValidationRuleExpirationDate                        | com.worldline.connect.sdk.client.android.model.validation
| com.ingenico.connect.gateway.sdk.client.android.sdk.validation                        | ValidationRuleFixedList                             | com.worldline.connect.sdk.client.android.model.validation
| com.ingenico.connect.gateway.sdk.client.android.sdk.validation                        | ValidationRuleIBAN                                  | com.worldline.connect.sdk.client.android.model.validation
| com.ingenico.connect.gateway.sdk.client.android.sdk.validation                        | ValidationRuleLength                                | com.worldline.connect.sdk.client.android.model.validation
| com.ingenico.connect.gateway.sdk.client.android.sdk.validation                        | ValidationRuleLuhn                                  | com.worldline.connect.sdk.client.android.model.validation
| com.ingenico.connect.gateway.sdk.client.android.sdk.validation                        | ValidationRuleRange                                 | com.worldline.connect.sdk.client.android.model.validation
| com.ingenico.connect.gateway.sdk.client.android.sdk.validation                        | ValidationRuleRegex                                 | com.worldline.connect.sdk.client.android.model.validation
| com.ingenico.connect.gateway.sdk.client.android.sdk.validation                        | ValidationRuleResidentIdNumber                      | com.worldline.connect.sdk.client.android.model.validation
| com.ingenico.connect.gateway.sdk.client.android.sdk.validation                        | ValidationRuleTermsAndConditions                    | com.worldline.connect.sdk.client.android.model.validation
| com.ingenico.connect.gateway.sdk.client.android.sdk.validation                        | ValidationType                                      | com.worldline.connect.sdk.client.android.model.validation
| com.ingenico.connect.gateway.sdk.client.android.sdk.network                           | ApiErrorResponse                                    | com.worldline.connect.sdk.client.android.model.network
| com.ingenico.connect.gateway.sdk.client.android.sdk.network                           | Error                                               | com.worldline.connect.sdk.client.android.model.network
| com.ingenico.connect.gateway.sdk.client.android.sdk.network                           | Success<X>                                          | com.worldline.connect.sdk.client.android.model.network
| com.ingenico.connect.gateway.sdk.client.android.sdk.network                           | ApiError                                            | com.worldline.connect.sdk.client.android.model.network
| com.ingenico.connect.gateway.sdk.client.android.sdk.network                           | Failure                                             | com.worldline.connect.sdk.client.android.model.network
| com.ingenico.connect.gateway.sdk.client.android.sdk.network                           | NetworkResponse                                     | com.worldline.connect.sdk.client.android.model.network
| com.ingenico.connect.gateway.sdk.client.android.sdk.network                           | UnknownNetworkResponseException                     | com.worldline.connect.sdk.client.android.model.network
| com.ingenico.connect.gateway.sdk.client.android.sdk.model                             | PaymentItemCacheKey                                 | com.worldline.connect.sdk.client.android.model.util
| com.ingenico.connect.gateway.sdk.client.android.sdk.model                             | Size                                                | com.worldline.connect.sdk.client.android.model.util
