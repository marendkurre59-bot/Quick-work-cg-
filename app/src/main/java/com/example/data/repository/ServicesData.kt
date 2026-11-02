package com.example.data.repository

import com.example.data.model.ServiceCategory
import com.example.data.model.ServiceItem

object ServicesData {
    val ALL_50_SERVICES: List<ServiceItem> = listOf(
        // 1. राजमिस्त्री (Mason)
        ServiceItem(
            id = "mas_rajmistri",
            title = "Raj Mistri (Mason) Brickwork & Construction",
            titleHindi = "राजमिस्त्री (Mason) ईंट जुड़ाई और निर्माण",
            category = ServiceCategory.MASONRY_CONSTRUCTION.englishName,
            categoryHindi = ServiceCategory.MASONRY_CONSTRUCTION.hindiName,
            iconKey = "masonry",
            basePrice = 699,
            timeEstimate = "Full / Half Day",
            description = "Certified skilled Raj Mistri for red brick/fly-ash block masonry, boundary wall repair, and foundation work.",
            descriptionHindi = "कुशल राजमिस्त्री ईंट जुड़ाई, दीवार निर्माण और मरम्मत कार्य के लिए।",
            isPopular = true,
            inclusions = listOf("Mortar ratio 1:4 mixing", "Line and plumb vertical check", "Curing advice", "Debris sweep")
        ),

        // 2. बढ़ई (Carpenter)
        ServiceItem(
            id = "carp_general",
            title = "Carpenter (बढ़ई) Door, Lock & Furniture Repair",
            titleHindi = "बढ़ई (Carpenter) दरवाजा, ताला और फर्नीचर काम",
            category = ServiceCategory.CARPENTRY.englishName,
            categoryHindi = ServiceCategory.CARPENTRY.hindiName,
            iconKey = "carpentry",
            basePrice = 249,
            timeEstimate = "30-60 mins",
            description = "Skilled carpenter for door locks, hinges, sliding wardrobe channels, table and chair repair.",
            descriptionHindi = "दरवाजे का ताला, कब्जा, स्लाइडिंग अलमारी और फर्नीचर ठीक करना।",
            isPopular = true,
            inclusions = listOf("Mortise lock replacement", "Hydraulic hinge align", "Drawer channel fix", "Wooden plugging")
        ),

        // 3. पेंटर (Painter)
        ServiceItem(
            id = "paint_painter",
            title = "Painter (पेंटर) Interior & Exterior Home Painting",
            titleHindi = "पेंटर (Painter) घर पुताई, प्राइमर और वॉल पुट्टी",
            category = ServiceCategory.PAINTING.englishName,
            categoryHindi = ServiceCategory.PAINTING.hindiName,
            iconKey = "painting",
            basePrice = 999,
            timeEstimate = "Scope Based",
            description = "Professional house painter for wall putty sanding, primer coating, and plastic emulsion roller painting.",
            descriptionHindi = "पुट्टी घिसाई, प्राइमर और डबल कोट रोलर पेंटिंग अनुभवी पेंटर द्वारा।",
            isPopular = true,
            inclusions = listOf("Wall sanding & cleaning", "Acrylic putty base", "Primer application", "2-coat emulsion finish")
        ),

        // 4. इलेक्ट्रिशियन (Electrician)
        ServiceItem(
            id = "elec_pro",
            title = "Electrician (इलेक्ट्रिशियन) Wiring & Appliance Repair",
            titleHindi = "इलेक्ट्रिशियन (Electrician) वायरिंग और फॉल्ट रिपेयर",
            category = ServiceCategory.ELECTRICAL.englishName,
            categoryHindi = ServiceCategory.ELECTRICAL.hindiName,
            iconKey = "electrical",
            basePrice = 199,
            timeEstimate = "30-45 mins",
            description = "Expert electrician for fan, switchboard, MCB short circuit, earthing, and house wiring check.",
            descriptionHindi = "पंखा, स्विचबोर्ड, एमसीबी शॉर्ट सर्किट, अर्थिंग और फॉल्ट ठीक करने के लिए।",
            isPopular = true,
            inclusions = listOf("Circuit continuity check", "Sparks & loose wire repair", "Switchboard tighten", "Safety earthing verify")
        ),

        // 5. प्लंबर (Plumber)
        ServiceItem(
            id = "plumb_pro",
            title = "Plumber (प्लंबर) Tap, Leakage & Pipe Repair",
            titleHindi = "प्लंबर (Plumber) नल, पाइप लीकेज और फिटिंग",
            category = ServiceCategory.PLUMBING_DRAIN.englishName,
            categoryHindi = ServiceCategory.PLUMBING_DRAIN.hindiName,
            iconKey = "plumbing",
            basePrice = 199,
            timeEstimate = "30-45 mins",
            description = "Experienced plumber for water tap leaks, concealed CPVC/GI pipe joints, flush cistern, and shower.",
            descriptionHindi = "नल का पानी टपकना, पाइप लीकेज, कमोड फ्लश और शावर फिटिंग।",
            isPopular = true,
            inclusions = listOf("Spindle & washer change", "CPVC cement solvent joint", "Leakage test under pressure", "Teflon seal re-wrap")
        ),

        // 6. सफाई कर्मचारी (Cleaner)
        ServiceItem(
            id = "clean_worker",
            title = "Cleaner (सफाई कर्मचारी) Deep Home & Washroom Cleaning",
            titleHindi = "सफाई कर्मचारी (Cleaner) घर और बाथरूम डीप सफाई",
            category = ServiceCategory.CLEANING_GARDEN.englishName,
            categoryHindi = ServiceCategory.CLEANING_GARDEN.hindiName,
            iconKey = "cleaning",
            basePrice = 499,
            timeEstimate = "60-90 mins",
            description = "Dedicated cleaner for floor scrubbing, cobweb removal, hard water stain descaling, and sanitation.",
            descriptionHindi = "फर्श धुलाई, जाले हटाना, टाइल्स के दाग छुड़ाना और कीटाणुनाशक सफाई।",
            isPopular = true,
            inclusions = listOf("Acid-free tile descaler", "Cobweb high ceiling sweep", "Mirror & tap shine", "Odor neutralizer")
        ),

        // 7. धोबी (Washerman / Laundry)
        ServiceItem(
            id = "dom_dhobi",
            title = "Dhobi (धोबी) Doorstep Clothes Wash & Steam Press",
            titleHindi = "धोबी (Washerman) कपड़े धुलाई और स्टीम प्रेस",
            category = ServiceCategory.DOMESTIC_HOUSEMAID.englishName,
            categoryHindi = ServiceCategory.DOMESTIC_HOUSEMAID.hindiName,
            iconKey = "laundry",
            basePrice = 199,
            timeEstimate = "24-48 Hours",
            description = "Doorstep pickup and delivery for fabric-care detergent washing, starching, and crisp heavy steam ironing.",
            descriptionHindi = "घर से कपड़े ले जाकर धुलाई, कलफ लगाना और स्टीम प्रेस कर वापस पहुंचाना।",
            isPopular = true,
            inclusions = listOf("Doorstep pickup & drop", "Color separation wash", "Heavy brass/steam press", "Hanger / neat fold packaging")
        ),

        // 8. मोची (Cobbler / Leather Repair)
        ServiceItem(
            id = "dom_cobbler",
            title = "Cobbler (मोची) Shoes, Bags & Leather Goods Repair",
            titleHindi = "मोची (Cobbler) जूता, चप्पल, बैग और लेदर रिपेयर",
            category = ServiceCategory.DOMESTIC_HOUSEMAID.englishName,
            categoryHindi = ServiceCategory.DOMESTIC_HOUSEMAID.hindiName,
            iconKey = "leather",
            basePrice = 149,
            timeEstimate = "30 mins",
            description = "Doorstep artisan for sole pasting, heavy nylon stitching, heel replacement, zipper repair, and polish.",
            descriptionHindi = "जूते का सोल चिपकाना, धागा सिलाई, चैन/जिपर ठीक करना और लेदर पॉलिश।",
            inclusions = listOf("Industrial shoe glue press", "Reinforced saddle stitch", "Zipper slider fix", "Wax cream buffing")
        ),

        // 9. रसोइया (Cook / Chef)
        ServiceItem(
            id = "dom_cook",
            title = "Cook / Maharaj (रसोइया) Daily Home & Party Food",
            titleHindi = "रसोइया / महाराज (Cook) दैनिक भोजन और पार्टी खाना",
            category = ServiceCategory.DOMESTIC_HOUSEMAID.englishName,
            categoryHindi = ServiceCategory.DOMESTIC_HOUSEMAID.hindiName,
            iconKey = "cook",
            basePrice = 499,
            timeEstimate = "Meal Based",
            description = "Hygienic verified cook for pure vegetarian, Chhattisgarhi delicacies, North Indian, and daily meals.",
            descriptionHindi = "स्वादिष्ट और शुद्ध घर का खाना, रोटी, दाल, सब्जी, चावल और पार्टी भोजन पकाने के लिए।",
            isPopular = true,
            inclusions = listOf("Customized spice level", "Hygienic prep with apron & cap", "Kitchen slab wiping post cook", "Family meal service")
        ),

        // 10. माली (Gardener / Landscaper)
        ServiceItem(
            id = "clean_gardener",
            title = "Gardener (माली) Lawn Mowing, Trimming & Plant Care",
            titleHindi = "माली (Gardener) गमला, लॉन कटाई और पौधों की देखभाल",
            category = ServiceCategory.CLEANING_GARDEN.englishName,
            categoryHindi = ServiceCategory.CLEANING_GARDEN.hindiName,
            iconKey = "garden",
            basePrice = 299,
            timeEstimate = "60-90 mins",
            description = "Expert gardener for grass trimming, organic vermicompost addition, weed removal, and plant repotting.",
            descriptionHindi = "घास कटाई, गमलों में खाद डालना, खरपतवार हटाना और पौधों की छंटाई।",
            isPopular = true,
            inclusions = listOf("Hedge pruning shears", "Organic khad vermicompost", "Soil tilling / godai", "Garden waste bagging")
        ),

        // 11. चौकीदार (Watchman / Security)
        ServiceItem(
            id = "sec_watchman",
            title = "Watchman (चौकीदार) Residential & Commercial Security",
            titleHindi = "चौकीदार (Watchman) घर, दुकान और गोदाम सुरक्षा",
            category = ServiceCategory.SECURITY_CARE.englishName,
            categoryHindi = ServiceCategory.SECURITY_CARE.hindiName,
            iconKey = "watchman",
            basePrice = 599,
            timeEstimate = "Shift / Daily",
            description = "Alert, verified night and day watchman for gates, construction sites, godowns, and housing societies.",
            descriptionHindi = "मकान, दुकान या सोसाइटी की पहरेदारी के लिए सतर्क और विश्वसनीय चौकीदार।",
            isPopular = true,
            inclusions = listOf("Visitor log entry maintenance", "Perimeter night patrol check", "Gate lock inspection", "Torch & whistle equipped")
        ),

        // 12. किसान और कृषि मजदूर (Farm Worker / Agriculture Labour)
        ServiceItem(
            id = "lab_farm",
            title = "Farm Worker (किसान और कृषि मजदूर) Crop & Field Labour",
            titleHindi = "किसान और कृषि मजदूर (Farm Worker) खेत जुताई व कटाई",
            category = ServiceCategory.LABOUR_FARM_TRANSPORT.englishName,
            categoryHindi = ServiceCategory.LABOUR_FARM_TRANSPORT.hindiName,
            iconKey = "tractor",
            basePrice = 450,
            timeEstimate = "Full Day",
            description = "Hardworking agriculture labour for paddy (Dhan) harvesting, weeding, fertilizer spray, and cattle care in CG.",
            descriptionHindi = "धान कटाई, मिंजाई, खाद छिड़काव, खेत का काम और कृषि मजदूरी।",
            isPopular = true,
            inclusions = listOf("Aadhaar identity verified", "Punctual field presence", "Paddy harvest & threshing", "Irrigation channel help")
        ),

        // 13. ठेकेदार (Contractor / All Contractor Services)
        ServiceItem(
            id = "mas_contractor",
            title = "All Contractor Services (ठेकेदार) Turnkey Construction",
            titleHindi = "ठेकेदार और निर्माण कार्य (Contractor Services)",
            category = ServiceCategory.MASONRY_CONSTRUCTION.englishName,
            categoryHindi = ServiceCategory.MASONRY_CONSTRUCTION.hindiName,
            iconKey = "contractor",
            basePrice = 1499,
            timeEstimate = "Consultation / Project",
            description = "Licensed building contractor for turnkey house construction, grey structure, material supply, and civil estimate.",
            descriptionHindi = "मकान निर्माण का ठेका, नक्शा अनुसार काम, सामग्री सहित व लेबर रेट ठेका।",
            isPopular = true,
            inclusions = listOf("Site visit & measurement", "Detailed BOQ material estimate", "Stage-wise milestones plan", "Structural stability verify")
        ),

        // 14. टाइल्स लगाने वाले कारीगर (Tile & Marble Worker)
        ServiceItem(
            id = "mas_tileworker",
            title = "Tile & Marble Worker (टाइल्स लगाने वाले कारीगर)",
            titleHindi = "टाइल्स लगाने वाले कारीगर (Tile & Marble Worker)",
            category = ServiceCategory.MASONRY_CONSTRUCTION.englishName,
            categoryHindi = ServiceCategory.MASONRY_CONSTRUCTION.hindiName,
            iconKey = "tiles",
            basePrice = 499,
            timeEstimate = "Per Sq Ft / Day",
            description = "Master tile mason for vitrified tiles, Italian marble, bathroom walls, kitchen granite slab, and epoxy grouting.",
            descriptionHindi = "फर्श की टाइल, मार्बल, किचन ग्रेनाइट प्लेटफॉर्म और बाथरूम टाइल्स फिटिंग।",
            isPopular = true,
            inclusions = listOf("Laser leveling alignment", "Chemical tile adhesive paste", "Diamond blade chamfer cut", "Epoxy stain-free grouting")
        ),

        // 15. छत और छप्पर का काम (Roofing & Tin Shed Worker)
        ServiceItem(
            id = "mas_roofing",
            title = "Roofing & Tin Shed Worker (छत और छप्पर का काम)",
            titleHindi = "छत और छप्पर का काम (Roofing & Tin Shed Worker)",
            category = ServiceCategory.MASONRY_CONSTRUCTION.englishName,
            categoryHindi = ServiceCategory.MASONRY_CONSTRUCTION.hindiName,
            iconKey = "roof",
            basePrice = 799,
            timeEstimate = "Scope Based",
            description = "Color-coated profile tin sheet roofing, terrace shed fabrication, water leak repair, and asbestos replacement.",
            descriptionHindi = "टीन शेड लगाना, लोहे का छप्पर, छत से पानी टपकना ठीक करना और गैल्वेनाइज्ड शीट।",
            inclusions = listOf("Iron truss framework drill", "Self-tapping rubber washer screws", "Gutter water spout routing", "Leak sealant silicone")
        ),

        // 16. वेल्डर (Welder & Iron Fabrication)
        ServiceItem(
            id = "mas_welder",
            title = "Welder (वेल्डर) Gate, Grill & Steel Fabrication",
            titleHindi = "वेल्डर (Welder) लोहे का गेट, ग्रिल और वेल्डिंग रिपेयर",
            category = ServiceCategory.MASONRY_CONSTRUCTION.englishName,
            categoryHindi = ServiceCategory.MASONRY_CONSTRUCTION.hindiName,
            iconKey = "welder",
            basePrice = 349,
            timeEstimate = "45 mins",
            description = "Mobile arc and MIG welding for broken gate hinges, balcony safety grills, channel gates, and iron railing.",
            descriptionHindi = "दरवाजे का टूटा कब्जा, खिड़की की ग्रिल, शटर और लोहे के सामान की पक्की वेल्डिंग।",
            isPopular = true,
            inclusions = listOf("Arc welding transformer setup", "Grinding smooth weld seam", "Anti-rust primer touchup", "High tensile rod weld")
        ),

        // 17. AC और फ्रिज मैकेनिक (AC & Refrigerator Mechanic)
        ServiceItem(
            id = "mech_acfridge",
            title = "AC & Refrigerator Mechanic (एसी और फ्रिज मैकेनिक)",
            titleHindi = "AC और फ्रिज मैकेनिक (AC & Fridge Mechanic)",
            category = ServiceCategory.MECHANIC_REPAIR.englishName,
            categoryHindi = ServiceCategory.MECHANIC_REPAIR.hindiName,
            iconKey = "ac_fridge",
            basePrice = 399,
            timeEstimate = "45 mins",
            description = "Split/Window AC foam jet service, compressor relay fix, gas charging, and double door fridge cooling troubleshooting.",
            descriptionHindi = "एसी की फोम जेट धुलाई, गैस लीकेज चेक, फ्रिज का कंप्रेसर और कूलिंग ठीक करना।",
            isPopular = true,
            inclusions = listOf("Jet pump coil foam rinse", "Compressor relay & OLP check", "Gas pressure gauge reading", "Drain choke cleanup")
        ),

        // 18. वाहन मैकेनिक (Two-Wheeler & Car Mechanic)
        ServiceItem(
            id = "mech_vehicle",
            title = "Vehicle Mechanic (वाहन मैकेनिक) Bike & Car Doorstep Repair",
            titleHindi = "वाहन मैकेनिक (Vehicle Mechanic) बाइक और कार रिपेयर",
            category = ServiceCategory.MECHANIC_REPAIR.englishName,
            categoryHindi = ServiceCategory.MECHANIC_REPAIR.hindiName,
            iconKey = "mechanic",
            basePrice = 299,
            timeEstimate = "30-45 mins",
            description = "Doorstep mechanic for bike/scooter starting trouble, engine oil change, tube/tubeless puncture, car battery jumpstart.",
            descriptionHindi = "घर बैठे बाइक-स्कूटर चालू ना होना, पंचर, इंजन ऑइल और कार बैटरी जंपस्टार्ट।",
            isPopular = true,
            inclusions = listOf("Spark plug & carburetor tune", "Brake pad tightening", "Battery booster cables", "Tubeless puncture repair")
        ),

        // 19. सभी प्रकार के लेबर और हेल्पर (General Labour)
        ServiceItem(
            id = "lab_general",
            title = "General Labour & Helper (सभी प्रकार के लेबर और हेल्पर)",
            titleHindi = "सभी प्रकार के लेबर और हेल्पर (General Labour)",
            category = ServiceCategory.LABOUR_FARM_TRANSPORT.englishName,
            categoryHindi = ServiceCategory.LABOUR_FARM_TRANSPORT.hindiName,
            iconKey = "labour",
            basePrice = 450,
            timeEstimate = "Full / Half Day",
            description = "Strong, polite, verified workers for house shifting, garden digging, heavy luggage carrying, and renovation help.",
            descriptionHindi = "सामान उठाना, घर शिफ्टिंग, मिट्टी खुदाई और निर्माण कार्य में मदद के लिए मेहनती मजदूर।",
            isPopular = true,
            inclusions = listOf("Aadhaar verified identity", "Full day punctual presence", "Heavy item carrying", "Polite & obedient conduct")
        ),

        // 20. Domestic Worker (घरेलू कामगार)
        ServiceItem(
            id = "dom_worker",
            title = "Domestic Worker (घरेलू कामगार) Daily Household Assistance",
            titleHindi = "घरेलू कामगार (Domestic Worker) रोजमर्रा घरेलू काम",
            category = ServiceCategory.DOMESTIC_HOUSEMAID.englishName,
            categoryHindi = ServiceCategory.DOMESTIC_HOUSEMAID.hindiName,
            iconKey = "domestic",
            basePrice = 399,
            timeEstimate = "Daily / Monthly",
            description = "Verified domestic helper for home organization, grocery shopping, elderly support, and general chore management.",
            descriptionHindi = "घर की दैनिक देखभाल, सामान व्यवस्थित करना और घरेलू कामों में सहयोग।",
            isPopular = true,
            inclusions = listOf("Background verified ID", "Household errand support", "Kitchen assistance", "Punctual daily attendance")
        ),

        // 21. Housemaid (घर की सफाई और घरेलू काम)
        ServiceItem(
            id = "dom_maid",
            title = "Housemaid (घर की सफाई और घरेलू काम) Jhadu, Pocha, Bartan",
            titleHindi = "Housemaid (घर की सफाई और घरेलू काम) झाड़ू-पोछा व बर्तन",
            category = ServiceCategory.DOMESTIC_HOUSEMAID.englishName,
            categoryHindi = ServiceCategory.DOMESTIC_HOUSEMAID.hindiName,
            iconKey = "maid",
            basePrice = 349,
            timeEstimate = "Daily / Monthly",
            description = "Reliable housemaid for daily sweeping, disinfectant mopping, utensil washing, and kitchen surface dusting.",
            descriptionHindi = "दैनिक झाड़ू-पोछा, बर्तन मांजना, किचन स्लैब की सफाई और डस्टिंग के लिए भरोसेमंद मेड।",
            isPopular = true,
            inclusions = listOf("Thorough floor sweeping", "Disinfectant phenyl mopping", "Sparkling dishwashing", "Dustbin garbage disposal")
        ),

        // 22. Private Driver (निजी ड्राइवर)
        ServiceItem(
            id = "pro_driver",
            title = "Private Driver (निजी ड्राइवर) Local & Outstation Car Driver",
            titleHindi = "निजी ड्राइवर (Private Driver) लोकल और बाहर यात्रा",
            category = ServiceCategory.PROFESSIONAL_SERVICES.englishName,
            categoryHindi = ServiceCategory.PROFESSIONAL_SERVICES.hindiName,
            iconKey = "driver",
            basePrice = 499,
            timeEstimate = "Hourly / Daily",
            description = "Licensed, courteous driver for manual/automatic cars. Raipur city commute or outstation trips across CG.",
            descriptionHindi = "ड्राइविंग लाइसेंस धारी कुशल ड्राइवर पर्सनल कार चलाने के लिए (लोकल व आउटस्टेशन)।",
            isPopular = true,
            inclusions = listOf("Valid Commercial/LMV license", "Safe defensive driving", "Navigation via GPS", "Car windshield wipe")
        ),

        // 23. Service Repair (मरम्मत सेवाएँ - General Handyman)
        ServiceItem(
            id = "mech_repair",
            title = "Service Repair (मरम्मत सेवाएँ) Multi-Trade Handyman",
            titleHindi = "मरम्मत सेवाएँ (Service Repair) घर की हर छोटी-बड़ी मरम्मत",
            category = ServiceCategory.MECHANIC_REPAIR.englishName,
            categoryHindi = ServiceCategory.MECHANIC_REPAIR.hindiName,
            iconKey = "repair",
            basePrice = 199,
            timeEstimate = "30-60 mins",
            description = "Multi-skilled handyman to fix curtain rods, hanging frames, small plumbing leaks, loose hinges, and screws.",
            descriptionHindi = "पर्दे की रॉड लगाना, फोटो फ्रेम, सीलन ठीक करना और घर की विविध मरम्मत।",
            isPopular = true,
            inclusions = listOf("Hammer drill anchor fixing", "Screwdriver tightening", "Sealant silicon apply", "Quick multi-task fix")
        ),

        // 24. Housekeeping (घर और ऑफिस की देखभाल)
        ServiceItem(
            id = "clean_housekeeping",
            title = "Housekeeping (घर और ऑफिस की देखभाल) Facility Care",
            titleHindi = "Housekeeping (घर और ऑफिस की देखभाल)",
            category = ServiceCategory.CLEANING_GARDEN.englishName,
            categoryHindi = ServiceCategory.CLEANING_GARDEN.hindiName,
            iconKey = "housekeeping",
            basePrice = 699,
            timeEstimate = "Day / Monthly Contract",
            description = "Trained housekeeping staff for commercial offices, banks, showrooms, and luxury residences in Chhattisgarh.",
            descriptionHindi = "ऑफिस, शोरूम और कोठी की दैनिक सफाई, टॉयलेट क्लीनिंग और देखभाल।",
            isPopular = true,
            inclusions = listOf("Glass cabin streak-free wipe", "Waste bin segregation", "Washroom hourly check", "Desk sanitization")
        ),

        // 25. Baby Care (बच्चों की देखभाल / Nanny)
        ServiceItem(
            id = "sec_babycare",
            title = "Baby Care (बच्चों की देखभाल) Nanny & Babysitter",
            titleHindi = "बच्चों की देखभाल (Baby Care / Nanny)",
            category = ServiceCategory.SECURITY_CARE.englishName,
            categoryHindi = ServiceCategory.SECURITY_CARE.hindiName,
            iconKey = "baby",
            basePrice = 499,
            timeEstimate = "Hourly / Daily Shift",
            description = "Gentle, police-verified nanny for infant feeding, diaper change, baby massage, bath, and bedtime story.",
            descriptionHindi = "बच्चों को संभालना, मालिश, नहलाना, समय पर खाना खिलाना और प्यार से देखभाल।",
            isPopular = true,
            inclusions = listOf("Police verified certificate", "Hygienic infant handling", "Bottle sterilization help", "Stimulating baby play")
        ),

        // 26. Home Medical Care (घर पर स्वास्थ्य देखभाल)
        ServiceItem(
            id = "sec_medical",
            title = "Home Medical Care (घर पर स्वास्थ्य देखभाल) Nurse & Caregiver",
            titleHindi = "घर पर स्वास्थ्य देखभाल (Home Medical Care / नर्स)",
            category = ServiceCategory.SECURITY_CARE.englishName,
            categoryHindi = ServiceCategory.SECURITY_CARE.hindiName,
            iconKey = "medical",
            basePrice = 599,
            timeEstimate = "Visit / Shift",
            description = "Qualified GNM/ANM nurse for BP/Sugar check, IV drip injection, wound dressing, catheter care, and elderly support.",
            descriptionHindi = "घर पर इंजेक्शन, ड्रिप लगाना, घाव की ड्रेसिंग, बीपी-शुगर जांच और बुजुर्गों की सेवा।",
            isPopular = true,
            inclusions = listOf("Vitals BP/Sugar/SpO2 check", "Sterile wound dressing", "Medication schedule monitor", "Elderly mobility support")
        ),

        // 27. Home Tuition (घर पर पढ़ाई / Home Tutor)
        ServiceItem(
            id = "pro_tuition",
            title = "Home Tuition (घर पर पढ़ाई) One-on-One Expert Tutor",
            titleHindi = "घर पर पढ़ाई (Home Tuition) होम ट्यूटर सेवा",
            category = ServiceCategory.PROFESSIONAL_SERVICES.englishName,
            categoryHindi = ServiceCategory.PROFESSIONAL_SERVICES.hindiName,
            iconKey = "tuition",
            basePrice = 399,
            timeEstimate = "1 Hour / Monthly",
            description = "Qualified subject teachers for CBSE/CG Board Class 1 to 12. Mathematics, Science, English, and exam prep.",
            descriptionHindi = "कक्षा 1 से 12 तक के लिए घर आकर पढ़ाने वाले योग्य शिक्षक (गणित, विज्ञान, अंग्रेजी)।",
            isPopular = true,
            inclusions = listOf("Concept clearing & doubt solving", "Daily homework assistance", "Weekly revision test", "Parent progress report")
        ),

        // 28. Guard Service (सुरक्षा गार्ड)
        ServiceItem(
            id = "sec_guard",
            title = "Guard Service (सुरक्षा गार्ड) Uniformed Security Personnel",
            titleHindi = "सुरक्षा गार्ड (Guard Service) वर्दीधारी सिक्योरिटी",
            category = ServiceCategory.SECURITY_CARE.englishName,
            categoryHindi = ServiceCategory.SECURITY_CARE.hindiName,
            iconKey = "guard",
            basePrice = 699,
            timeEstimate = "8/12 Hour Shift",
            description = "Trained uniformed security guards for residential townships, ATMs, hospitals, and events across Chhattisgarh.",
            descriptionHindi = "अनुशासित वर्दीधारी सुरक्षा गार्ड गेट सुरक्षा, सोसाइटी और शोरूम के लिए।",
            isPopular = true,
            inclusions = listOf("PSARA compliant trained guard", "Full uniform with cap & baton", "Visitor frisking and register", "Emergency protocol ready")
        ),

        // 29. Bodyguard (निजी सुरक्षा / PSO)
        ServiceItem(
            id = "sec_bodyguard",
            title = "Bodyguard (निजी सुरक्षा / PSO) Personal Security Officer",
            titleHindi = "निजी सुरक्षा (Bodyguard / PSO) पर्सनल सिक्योरिटी",
            category = ServiceCategory.SECURITY_CARE.englishName,
            categoryHindi = ServiceCategory.SECURITY_CARE.hindiName,
            iconKey = "bodyguard",
            basePrice = 1999,
            timeEstimate = "Day / Event Scope",
            description = "Elite physically trained, vigilant Armed/Unarmed Personal Security Officer (PSO) for VIPs and business executives.",
            descriptionHindi = "वीआईपी, व्यापारियों और महत्वपूर्ण व्यक्तियों के लिए उच्च प्रशिक्षित निजी सुरक्षा कमांडो।",
            inclusions = listOf("Certified close protection training", "Threat perimeter scanning", "Escort during commute", "Discreet professional demeanor")
        ),

        // 30. Gutter Cleaner (नाली और गटर की सफाई)
        ServiceItem(
            id = "plumb_gutter",
            title = "Gutter Cleaner (नाली और गटर की सफाई) Unclogging Machine",
            titleHindi = "नाली और गटर की सफाई (Gutter Cleaner) मशीन डी-चोक",
            category = ServiceCategory.PLUMBING_DRAIN.englishName,
            categoryHindi = ServiceCategory.PLUMBING_DRAIN.hindiName,
            iconKey = "gutter",
            basePrice = 499,
            timeEstimate = "45-60 mins",
            description = "Heavy-duty electric spring snake cable and high-pressure suction to clear choked underground society sewer drains.",
            descriptionHindi = "जाम गटर और नाली से कचरा निकालना, बदबू दूर करना और मशीन से सफाई।",
            isPopular = true,
            inclusions = listOf("Steel spring rotary snake run", "Solid silt & plastic removal", "Bleaching & lime powder spread", "Free flowing drainage check")
        ),

        // 31. At-home Beauty & Salon (घर पर ब्यूटी और सैलून)
        ServiceItem(
            id = "pro_beauty",
            title = "At-home Beauty & Salon (घर पर ब्यूटी और सैलून) For Women",
            titleHindi = "घर पर ब्यूटी और सैलून (At-home Beauty & Salon)",
            category = ServiceCategory.PROFESSIONAL_SERVICES.englishName,
            categoryHindi = ServiceCategory.PROFESSIONAL_SERVICES.hindiName,
            iconKey = "beauty",
            basePrice = 499,
            timeEstimate = "45-60 mins",
            description = "Certified beauticians for chocolate waxing, facial, fruit cleanup, manicure, pedicure, and bridal hair styling at home.",
            descriptionHindi = "घर बैठे फेशियल, वैक्सिंग, हेयर कटिंग, मैनीक्योर और ब्राइडल मेहंदी सेवाएं।",
            isPopular = true,
            inclusions = listOf("Single-use disposable kit", "O3+ / Lotus organic facial kit", "Post-wax soothing gel", "Complete mess-free cleanup")
        ),

        // 32. Employee Agency (कर्मचारी उपलब्ध कराने वाली एजेंसी)
        ServiceItem(
            id = "pro_agency",
            title = "Employee Agency (कर्मचारी उपलब्ध कराने वाली एजेंसी) Staffing",
            titleHindi = "कर्मचारी एजेंसी (Employee Agency) मैनपावर स्टाफिंग",
            category = ServiceCategory.PROFESSIONAL_SERVICES.englishName,
            categoryHindi = ServiceCategory.PROFESSIONAL_SERVICES.hindiName,
            iconKey = "agency",
            basePrice = 999,
            timeEstimate = "Immediate Placement",
            description = "Direct manpower supply: verified factory workers, office boys, receptionists, delivery boys, and sales staff.",
            descriptionHindi = "फैक्ट्री वर्कर, ऑफिस बॉय, रिसेप्शनिस्ट, सेल्स स्टाफ और मैनपावर सप्लाई।",
            isPopular = true,
            inclusions = listOf("Resume screening & candidate interview", "Aadhaar & Police check", "Replacement guarantee within 30d", "Fast 24h deployment")
        ),

        // 33. Transport Booking (वाहन और सामान परिवहन)
        ServiceItem(
            id = "lab_transport",
            title = "Transport Booking (वाहन और सामान परिवहन) Mini Truck / Auto",
            titleHindi = "वाहन और सामान परिवहन (Transport Booking) छोटा हाथी",
            category = ServiceCategory.LABOUR_FARM_TRANSPORT.englishName,
            categoryHindi = ServiceCategory.LABOUR_FARM_TRANSPORT.hindiName,
            iconKey = "transport",
            basePrice = 799,
            timeEstimate = "Instant Booking",
            description = "Tata Ace (Chhota Hathi), Mahindra Bolero Pickup, and Loading Auto for household shifting and commercial goods.",
            descriptionHindi = "घर का सामान, फर्नीचर और व्यापारिक माल पहुंचाने के लिए लोडिंग वाहन बुकिंग।",
            isPopular = true,
            inclusions = listOf("GPS tracked loading vehicle", "Tarpaulin rain cover provided", "Punctual doorstep arrival", "Driver luggage assist")
        ),

        // 34. Catering & Decoration (कैटरिंग और सजावट)
        ServiceItem(
            id = "pro_catering",
            title = "Catering & Decoration (कैटरिंग और सजावट) Events & Parties",
            titleHindi = "कैटरिंग और सजावट (Catering & Decoration) शादी व पार्टी",
            category = ServiceCategory.PROFESSIONAL_SERVICES.englishName,
            categoryHindi = ServiceCategory.PROFESSIONAL_SERVICES.hindiName,
            iconKey = "catering",
            basePrice = 1999,
            timeEstimate = "Event Based",
            description = "Halwai catering, buffet layout, balloon decoration, flower entry arch, and DJ sound setup for birthdays & weddings.",
            descriptionHindi = "शादी, जन्मदिन और मांगलिक कार्यक्रमों के लिए स्वादिष्ट कैटरिंग व टेंट-लाइट सजावट।",
            isPopular = true,
            inclusions = listOf("Tasting menu consultation", "Chafing dish buffet counters", "Balloon / flower themed stage", "Uniformed serving staff")
        ),

        // 35. Real Estate (मकान और प्रॉपर्टी सेवाएँ)
        ServiceItem(
            id = "pro_realestate",
            title = "Real Estate (मकान और प्रॉपर्टी सेवाएँ) Rent, Buy & Sell CG",
            titleHindi = "मकान और प्रॉपर्टी सेवाएँ (Real Estate) किराया व खरीद",
            category = ServiceCategory.PROFESSIONAL_SERVICES.englishName,
            categoryHindi = ServiceCategory.PROFESSIONAL_SERVICES.hindiName,
            iconKey = "realestate",
            basePrice = 499,
            timeEstimate = "Consultation",
            description = "Verified rental flats, independent houses, commercial shops, agricultural plots, and registry documentation in CG.",
            descriptionHindi = "किराए पर मकान, दुकान, प्लॉट खरीद-बिक्री और रजिस्ट्री दस्तावेज सहायता।",
            isPopular = true,
            inclusions = listOf("Direct owner verified listings", "Site inspection accompaniment", "Rent agreement drafting", "RERA compliance check")
        ),

        // 36. Financial Advisor (वित्तीय सलाहकार)
        ServiceItem(
            id = "pro_finance",
            title = "Financial Advisor (वित्तीय सलाहकार) Loan, Subsidy & Tax",
            titleHindi = "वित्तीय सलाहकार (Financial Advisor) लोन, बीमा और सब्सिडी",
            category = ServiceCategory.PROFESSIONAL_SERVICES.englishName,
            categoryHindi = ServiceCategory.PROFESSIONAL_SERVICES.hindiName,
            iconKey = "finance",
            basePrice = 399,
            timeEstimate = "30-45 mins",
            description = "Expert guidance for Mudra loans, Home loans, Kisan Credit Card, CG state business subsidies, ITR, and Insurance.",
            descriptionHindi = "मुद्रा लोन, होम लोन, किसान क्रेडिट कार्ड, सरकारी सब्सिडी, जीएसटी और बीमा सलाह।",
            isPopular = true,
            inclusions = listOf("Eligibility criteria calculation", "Government subsidy matching", "Documentation audit checklist", "Bank liaising advice")
        ),

        // 37. Ceiling Fan Installation & Repair
        ServiceItem(
            id = "elec_fan",
            title = "Ceiling & Exhaust Fan Repair/Install",
            titleHindi = "पंखा रिपेयर और नया इंस्टालेशन",
            category = ServiceCategory.ELECTRICAL.englishName,
            categoryHindi = ServiceCategory.ELECTRICAL.hindiName,
            iconKey = "fan",
            basePrice = 199,
            timeEstimate = "30 mins",
            description = "Capacitor check, bearing noise fix, speed regulator, and ceiling fan replacement.",
            descriptionHindi = "कैपेसिटर जांच, बेयरिंग ग्रीसिंग और कंपन-मुक्त फिटिंग।",
            inclusions = listOf("Capacitor replacement", "Bearing noise fix", "Safety clamp tighten", "Regulator test")
        ),

        // 38. Switchboard & Socket Wiring Repair
        ServiceItem(
            id = "elec_switch",
            title = "Switchboard & Socket Wiring Repair",
            titleHindi = "स्विचबोर्ड और सॉकेट वायरिंग मरम्मत",
            category = ServiceCategory.ELECTRICAL.englishName,
            categoryHindi = ServiceCategory.ELECTRICAL.hindiName,
            iconKey = "power",
            basePrice = 149,
            timeEstimate = "20-30 mins",
            description = "Burnt switch replacement, loose contact troubleshooting, and heavy-duty 16A socket install.",
            descriptionHindi = "जले स्विच बदलना, ढीले तार ठीक करना और 16A पावर सॉकेट लगाना।",
            inclusions = listOf("Sparks & humming fix", "16A AC/Geyser point", "Internal wiring continuity test", "Child-safe shutters")
        ),

        // 39. Inverter & Home UPS Installation
        ServiceItem(
            id = "elec_inverter",
            title = "Inverter & Home UPS Setup",
            titleHindi = "इन्वर्टर और बैटरी कनेक्शन सेटअप",
            category = ServiceCategory.ELECTRICAL.englishName,
            categoryHindi = ServiceCategory.ELECTRICAL.hindiName,
            iconKey = "battery",
            basePrice = 499,
            timeEstimate = "45 mins",
            description = "Complete home inverter installation, distilled water top-up, and terminal cleaning.",
            descriptionHindi = "होम इन्वर्टर फिटिंग, डिस्टिल्ड वाटर टॉप-अप और टर्मिनल की सफाई।",
            inclusions = listOf("Tubular battery terminal grease", "Bypass switch installation", "Back-up load testing", "Acid leak check")
        ),

        // 40. Water Tank High-Pressure Cleaning
        ServiceItem(
            id = "plumb_tank",
            title = "Water Tank High-Pressure Cleaning",
            titleHindi = "पानी टंकी की डीप सफाई (प्रेशर वॉश)",
            category = ServiceCategory.PLUMBING_DRAIN.englishName,
            categoryHindi = ServiceCategory.PLUMBING_DRAIN.hindiName,
            iconKey = "tank",
            basePrice = 599,
            timeEstimate = "60 mins",
            description = "Scientific cleaning with sludge vacuum pump, high pressure jet, and UV sanitization.",
            descriptionHindi = "कीचड़ हटाना, हाई प्रेशर जेट धुलाई और एंटी-बैक्टीरियल सफाई।",
            inclusions = listOf("Sludge extraction vacuum", "High power jet wash", "Algae scrub off", "Potassium permanganate treatment")
        ),

        // 41. Geyser Installation & Heating Coil Repair
        ServiceItem(
            id = "elec_geyser",
            title = "Geyser Installation & Coil Repair",
            titleHindi = "गीजर फिटिंग और हीटिंग एलिमेंट रिपेयर",
            category = ServiceCategory.ELECTRICAL.englishName,
            categoryHindi = ServiceCategory.ELECTRICAL.hindiName,
            iconKey = "geyser",
            basePrice = 349,
            timeEstimate = "45 mins",
            description = "Instant/Storage water heater electrical heating coil replacement and thermostat tuning.",
            descriptionHindi = "हीटिंग कॉइल बदलना, थर्मोस्टेट सेटिंग और शॉक-प्रूफ अर्थिंग।",
            inclusions = listOf("Thermostat calibration", "Heating element descaling", "Earth leakage inspection", "Safety valve check")
        ),

        // 42. Submersible & Motor Pump Fix
        ServiceItem(
            id = "plumb_pump",
            title = "Submersible & Monoblock Pump Fix",
            titleHindi = "सबमर्सिबल और मोटर पंप रिपेयर",
            category = ServiceCategory.PLUMBING_DRAIN.englishName,
            categoryHindi = ServiceCategory.PLUMBING_DRAIN.hindiName,
            iconKey = "pump",
            basePrice = 649,
            timeEstimate = "60 mins",
            description = "Priming issue resolution, capacitor replacement, impeller check, and auto-cut controller.",
            descriptionHindi = "मोटर जाम खुलवाना, कैपेसिटर चेंज और ऑटो-कट सेंसर लगाना।",
            inclusions = listOf("Air vent priming check", "Foot-valve testing", "Capacitor test", "Float switch sensor")
        ),

        // 43. RO Water Purifier Service & Filter Change
        ServiceItem(
            id = "mech_ro",
            title = "RO Water Purifier Service & Filter Change",
            titleHindi = "आरओ वाटर प्यूरीफायर सर्विस और फ़िल्टर",
            category = ServiceCategory.MECHANIC_REPAIR.englishName,
            categoryHindi = ServiceCategory.MECHANIC_REPAIR.hindiName,
            iconKey = "water_drop",
            basePrice = 399,
            timeEstimate = "45 mins",
            description = "TDS level calibration, sediment and carbon filter flush, booster pump and SMPS check.",
            descriptionHindi = "टीडीएस लेवल सेट करना, सेडिमेंट व कार्बन फ़िल्टर बदलना और पंप चेकिंग।",
            inclusions = listOf("Digital TDS tester reading", "Pre-filter spun candle flush", "Auto-shutoff solenoid valve", "UV lamp check")
        ),

        // 44. Kitchen Electric Chimney Deep Degrease
        ServiceItem(
            id = "mech_chimney",
            title = "Kitchen Chimney Deep Degrease & Wash",
            titleHindi = "किचन चिमनी डीप ऑइल क्लीनिंग",
            category = ServiceCategory.MECHANIC_REPAIR.englishName,
            categoryHindi = ServiceCategory.MECHANIC_REPAIR.hindiName,
            iconKey = "kitchen",
            basePrice = 499,
            timeEstimate = "60 mins",
            description = "Baffle and mesh filter chemical degreasing, motor carbon cleaning, and oil collector empty.",
            descriptionHindi = "चिमनी से चिपचिपा तेल हटाना और मोटर पंखे की केमिकल धुलाई।",
            inclusions = listOf("Caustic soda bath for filters", "Exhaust duct suction check", "Touch panel sensory test", "Outer steel polish")
        ),

        // 45. Smart LED TV Wall Mounting & Setup
        ServiceItem(
            id = "mech_tv",
            title = "Smart LED TV Wall Mount & Setup",
            titleHindi = "स्मार्ट टीवी वॉल माउंटिंग और केबलिंग",
            category = ServiceCategory.MECHANIC_REPAIR.englishName,
            categoryHindi = ServiceCategory.MECHANIC_REPAIR.hindiName,
            iconKey = "tv",
            basePrice = 299,
            timeEstimate = "30 mins",
            description = "Heavy duty steel wall bracket install, concealed cable routing, and audio sync.",
            descriptionHindi = "दीवार पर सुरक्षित ब्रैकेट फिटिंग, वायरिंग छिपाना और सेटअप।",
            inclusions = listOf("Wall drill anchor studs", "VESA mount alignment", "HDMI / Box connection", "Sound bar audio alignment")
        ),

        // 46. Terrace Roof Leak Waterproofing
        ServiceItem(
            id = "mas_waterproof",
            title = "Terrace Roof Leak Waterproofing",
            titleHindi = "छत लीकेज और वाटरप्रूफिंग कोटिंग",
            category = ServiceCategory.MASONRY_CONSTRUCTION.englishName,
            categoryHindi = ServiceCategory.MASONRY_CONSTRUCTION.hindiName,
            iconKey = "roofing",
            basePrice = 899,
            timeEstimate = "90 mins",
            description = "Elastomeric waterproof membrane coating to stop rain water leakage.",
            descriptionHindi = "छत से पानी टपकना रोकने के लिए 3-कोट वाटरप्रूफिंग।",
            inclusions = listOf("High-pressure dust clean", "Polymer crack compound", "Primer base application", "UV top membrane")
        ),

        // 47. Anti-Termite Wood & Soil Drill
        ServiceItem(
            id = "clean_termite",
            title = "Anti-Termite (Deemak) Wood & Soil Drill",
            titleHindi = "दीमक का पक्का इलाज (ड्रिलिंग ट्रीटमेंट)",
            category = ServiceCategory.CLEANING_GARDEN.englishName,
            categoryHindi = ServiceCategory.CLEANING_GARDEN.hindiName,
            iconKey = "pest_control",
            basePrice = 899,
            timeEstimate = "60-90 mins",
            description = "Subterranean termite control with 12mm wall-floor junction drilling and odorless chemical injection.",
            descriptionHindi = "दीवार और लकड़ी में ड्रिल करके गंधहीन दीमक दवा डालना।",
            inclusions = listOf("12mm perimeter hole drilling", "Govt-approved termiticide push", "Color matching chalk plugging", "1 Year written warranty")
        ),

        // 48. House Shifting Packers & Movers
        ServiceItem(
            id = "lab_packers",
            title = "House Shifting Packers & Movers",
            titleHindi = "घर शिफ्टिंग पैकर्स एंड मूवर्स",
            category = ServiceCategory.LABOUR_FARM_TRANSPORT.englishName,
            categoryHindi = ServiceCategory.LABOUR_FARM_TRANSPORT.hindiName,
            iconKey = "local_shipping",
            basePrice = 1999,
            timeEstimate = "Half / Full Day",
            description = "Bubble wrap, corrugated sheet packing of fragile items, furniture loading and safe transit.",
            descriptionHindi = "बबल रैप पैकिंग, फर्नीचर लोडिंग और सुरक्षित घर शिफ्टिंग।",
            inclusions = listOf("3-layer bubble packaging", "Fragile glassware foam box", "Dismantling & re-assembly", "Safe vehicle loading")
        ),

        // 49. HD CCTV Camera Setup & 4CH DVR
        ServiceItem(
            id = "sec_cctv",
            title = "HD CCTV Camera Setup & 4CH DVR",
            titleHindi = "सीसीटीवी कैमरा और डीवीआर इंस्टालेशन",
            category = ServiceCategory.SECURITY_CARE.englishName,
            categoryHindi = ServiceCategory.SECURITY_CARE.hindiName,
            iconKey = "videocam",
            basePrice = 799,
            timeEstimate = "60 mins",
            description = "Bullet and dome camera angle tuning, BNC crimping, and mobile live viewing setup.",
            descriptionHindi = "कैमरा लगाना, केबल बिछाना और मोबाइल पर लाइव व्यू चालू करना।",
            inclusions = listOf("Rigid wall bracket mount", "RG59 coaxial wire routing", "Hard drive formatting", "Mobile app remote setup")
        ),

        // 50. Rooftop Solar Panel Cleaning & Maintenance
        ServiceItem(
            id = "sec_solar",
            title = "Rooftop Solar Panel Cleaning & Maintenance",
            titleHindi = "सोलर पैनल की सफाई और मेंटेनेंस",
            category = ServiceCategory.SECURITY_CARE.englishName,
            categoryHindi = ServiceCategory.SECURITY_CARE.hindiName,
            iconKey = "solar_power",
            basePrice = 499,
            timeEstimate = "45 mins",
            description = "De-ionized water soft wipe, dust removal for +25% solar efficiency, inverter voltage check.",
            descriptionHindi = "सोलर पैनल से धूल हटाकर बिजली उत्पादन 25% तक बढ़ाना।",
            inclusions = listOf("Non-scratch telescopic wiper", "DC string voltage measurement", "MC4 connector tight check", "Generation kW comparison")
        )
    )
}
