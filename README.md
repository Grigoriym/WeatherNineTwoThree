It is a test project.

This has to work with "developer here api", but their documentation is bad and some services work randomly, that is why I migrated to geoapify and openweather.
Some problems I bumped into:
1) On API 21 there was a "java.security.cert.CertPathValidatorException: Trust anchor for certification path not found" with openweather api.
2) SearchCityFragment editText is covered with some duct tapes for a correct behavior (save state of a dropdown, save state of Ok button, save state of editText text, do not make excessive requests) in such cases:
   1) Correct behavior on "Don't keep activities"
   2) Correct behavior on configuration changed
   3) Correct behavior on onBackPressed from details
   4) Correct behavior on APIs: 21, 23, 26, 30, 31
3) SharedFlow with replay=1 grants us the ability to repeat the request and send the data to UI on config changes, etc. while ignoring StateFlow equals() functionality to send the object if it is equals to an old one.