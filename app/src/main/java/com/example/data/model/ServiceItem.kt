package com.example.data.model

data class ServiceItem(
    val id: String,
    val title: String,
    val titleHindi: String,
    val category: String,
    val categoryHindi: String,
    val iconKey: String,
    val basePrice: Int,
    val timeEstimate: String,
    val description: String,
    val descriptionHindi: String,
    val isPopular: Boolean = false,
    val isEmergencyEligible: Boolean = true,
    val inclusions: List<String>,
    val serialNumber: Int = 1
)

enum class ServiceCategory(
    val englishName: String,
    val hindiName: String,
    val iconKey: String
) {
    MASONRY_CONSTRUCTION("Masonry & Construction", "राजमिस्त्री और निर्माण", "masonry"),
    CARPENTRY("Carpentry & Furniture", "बढ़ई और फर्नीचर", "carpentry"),
    PAINTING("Painting & Renovation", "पेंटर और पुताई", "painting"),
    ELECTRICAL("Electrical & Power", "इलेक्ट्रिशियन और बिजली", "electrical"),
    PLUMBING_DRAIN("Plumbing & Gutter", "प्लंबर और नाली/गटर", "plumbing"),
    MECHANIC_REPAIR("Mechanic & Appliance", "मैकेनिक और रिपेयर", "mechanic"),
    DOMESTIC_HOUSEMAID("Domestic & Housemaid", "घरेलू कामगार और मेड", "domestic"),
    CLEANING_GARDEN("Cleaning & Garden", "सफाई, हाउसकीपिंग व माली", "cleaning"),
    SECURITY_CARE("Security & Care", "सुरक्षा गार्ड और देखभाल", "security"),
    LABOUR_FARM_TRANSPORT("Labour, Farm & Transport", "मजदूर, कृषि और ट्रांसपोर्ट", "labour"),
    PROFESSIONAL_SERVICES("Professional & Care", "ट्यूशन, ब्यूटी और सेवाएं", "professional")
}

object PartnerTradesList {
    val ALL_TRADES = listOf(
        "1. राजमिस्त्री (Mason)",
        "2. बढ़ई (Carpenter)",
        "3. पेंटर (Painter)",
        "4. इलेक्ट्रिशियन (Electrician)",
        "5. प्लंबर (Plumber)",
        "6. टाइल्स मिस्त्री (Tile Worker)",
        "7. छत और छप्पर (Roofing Worker)",
        "8. वेल्डर (Welder)",
        "9. ठेकेदार (Contractor)",
        "10. लेबर / हेल्पर (General Labour)",
        "11. किसान / कृषि मजदूर (Farm Worker)",
        "12. एसी और फ्रिज (AC & Fridge Mechanic)",
        "13. वाहन मैकेनिक (Vehicle Mechanic)",
        "14. घरेलू उपकरण (Appliance Repair)",
        "15. घरेलू कामगार (Domestic Worker)",
        "16. हाउसमेड (Housemaid)",
        "17. सफाई कर्मचारी (Cleaner)",
        "18. धोबी (Washerman)",
        "19. मोची (Cobbler)",
        "20. रसोइया (Cook)",
        "21. माली (Gardener)",
        "22. हाउसकीपिंग (Housekeeping)",
        "23. बच्चों की देखभाल (Baby Care)",
        "24. घर पर स्वास्थ्य देखभाल (Home Medical Care)",
        "25. नाली/गटर सफाई (Gutter Cleaner)",
        "26. ब्यूटी और सैलून (Beauty & Salon)",
        "27. निजी ड्राइवर (Private Driver)",
        "28. सुरक्षा गार्ड (Security Guard)",
        "29. बॉडीगार्ड (Bodyguard)",
        "30. मरम्मत सेवाएं (Service Repair)",
        "31. कर्मचारी एजेंसी (Employee Agency)",
        "32. परिवहन / ढुलाई (Transport Booking)",
        "33. कैटरिंग और डेकोरेशन (Catering & Decoration)",
        "34. होम ट्यूशन (Home Tuition)",
        "35. रियल एस्टेट (Real Estate)",
        "36. वित्तीय सलाहकार (Financial Advisor)",
        "37. कंप्यूटर और मोबाइल (Computer & Mobile Repair)",
        "38. डिलीवरी कर्मचारी (Delivery Worker)",
        "39. पेंटर हेल्पर (Painter Helper)",
        "40. निर्माण सुपरवाइजर (Construction Supervisor)",
        "41. प्लंबर हेल्पर (Plumber Helper)",
        "42. इलेक्ट्रिशियन हेल्पर (Electrician Helper)",
        "43. डिजिटल वर्क / सीएससी (Digital Work)",
        "44. सोलर और इन्वर्टर (Solar & Inverter)",
        "45. वाटर टैंक सफाई (Water Tank Wash)"
    )
}
