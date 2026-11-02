package com.example.data.model

data class CoverageCity(
    val id: String,
    val name: String,
    val nameHindi: String,
    val region: RegionType,
    val isHub: Boolean = false,
    val localities: List<String>
)

enum class RegionType(val label: String, val labelHindi: String) {
    CHHATTISGARH("Chhattisgarh (BCG/CG)", "छत्तीसगढ़"),
    ALL_INDIA("All India", "अखिल भारतीय")
}

object CoverageData {
    val CITIES = listOf(
        // Chhattisgarh Primary Network (BCG - Bhilai / Champa / Bilaspur / CG)
        CoverageCity(
            id = "raipur",
            name = "Raipur",
            nameHindi = "रायपुर",
            region = RegionType.CHHATTISGARH,
            isHub = true,
            localities = listOf("Telibandha", "Shankar Nagar", "Pandri", "Devendra Nagar", "Tatibandh", "Civil Lines", "Samta Colony", "Bhatagaon")
        ),
        CoverageCity(
            id = "bhilai_durg",
            name = "Bhilai - Durg",
            nameHindi = "भिलाई - दुर्ग",
            region = RegionType.CHHATTISGARH,
            isHub = true,
            localities = listOf("Supela", "Nehru Nagar", "Sector 6", "Power House", "Smriti Nagar", "Durg Station", "Mohan Nagar", "Padmanabhpur")
        ),
        CoverageCity(
            id = "bilaspur",
            name = "Bilaspur",
            nameHindi = "बिलासपुर",
            region = RegionType.CHHATTISGARH,
            isHub = true,
            localities = listOf("Vyas Nagar", "Mangla", "Rajkishore Nagar", "Torwa", "Link Road", "Sipat Road", "Uslapur")
        ),
        CoverageCity(
            id = "korba",
            name = "Korba",
            nameHindi = "कोरबा",
            region = RegionType.CHHATTISGARH,
            localities = listOf("Transport Nagar", "TP Nagar", "NTPC Colony", "Balco Nagar", "Darri", "Kosabadi")
        ),
        CoverageCity(
            id = "rajnandgaon",
            name = "Rajnandgaon",
            nameHindi = "राजनांदगांव",
            region = RegionType.CHHATTISGARH,
            localities = listOf("Ganj Para", "Kailash Nagar", "Basantpur", "Lakhmi Nagar", "Station Road")
        ),
        CoverageCity(
            id = "raigarh",
            name = "Raigarh",
            nameHindi = "रायगढ़",
            region = RegionType.CHHATTISGARH,
            localities = listOf("Chakradhar Nagar", "Boirdadar", "Station Chowk", "Kelo Vihar")
        ),
        CoverageCity(
            id = "jagdalpur",
            name = "Jagdalpur (Bastar)",
            nameHindi = "जगदलपुर",
            region = RegionType.CHHATTISGARH,
            localities = listOf("Dharampura", "Geedam Road", "Sanjay Market", "Kanker Naka")
        ),
        CoverageCity(
            id = "ambikapur",
            name = "Ambikapur (Surguja)",
            nameHindi = "अंबिकापुर",
            region = RegionType.CHHATTISGARH,
            localities = listOf("Kedarpur", "Gandhinagar", "Banaras Road", "Manendragarh Road")
        ),

        // All India Expansion Network
        CoverageCity(
            id = "delhi_ncr",
            name = "Delhi NCR",
            nameHindi = "दिल्ली एनसीआर",
            region = RegionType.ALL_INDIA,
            isHub = true,
            localities = listOf("Connaught Place", "Noida Sector 62", "Gurugram CyberHub", "Dwarka", "Indirapuram")
        ),
        CoverageCity(
            id = "mumbai",
            name = "Mumbai",
            nameHindi = "मुंबई",
            region = RegionType.ALL_INDIA,
            isHub = true,
            localities = listOf("Andheri West", "Bandra Kurla Complex", "Thane West", "Navi Mumbai Vashi", "Borivali")
        ),
        CoverageCity(
            id = "bengaluru",
            name = "Bengaluru",
            nameHindi = "बेंगलुरु",
            region = RegionType.ALL_INDIA,
            isHub = true,
            localities = listOf("Koramangala", "HSR Layout", "Indiranagar", "Whitefield", "Electronic City")
        ),
        CoverageCity(
            id = "pune",
            name = "Pune",
            nameHindi = "पुणे",
            region = RegionType.ALL_INDIA,
            localities = listOf("Hinjewadi", "Kothrud", "Viman Nagar", "Wakad", "Baner")
        ),
        CoverageCity(
            id = "hyderabad",
            name = "Hyderabad",
            nameHindi = "हैदराबाद",
            region = RegionType.ALL_INDIA,
            localities = listOf("Gachibowli", "HITEC City", "Madhapur", "Kukatpally", "Banjara Hills")
        ),
        CoverageCity(
            id = "kolkata",
            name = "Kolkata",
            nameHindi = "कोलकाता",
            region = RegionType.ALL_INDIA,
            localities = listOf("Salt Lake City", "New Town", "Park Street", "Dum Dum", "Howrah")
        ),
        CoverageCity(
            id = "patna",
            name = "Patna",
            nameHindi = "पटना",
            region = RegionType.ALL_INDIA,
            localities = listOf("Boring Road", "Kankarbagh", "Bailey Road", "Danapur")
        ),
        CoverageCity(
            id = "lucknow",
            name = "Lucknow",
            nameHindi = "लखनऊ",
            region = RegionType.ALL_INDIA,
            localities = listOf("Gomti Nagar", "Hazratganj", "Alambagh", "Indira Nagar")
        ),
        CoverageCity(
            id = "jaipur",
            name = "Jaipur",
            nameHindi = "जयपुर",
            region = RegionType.ALL_INDIA,
            localities = listOf("Malviya Nagar", "Vaishali Nagar", "Mansarovar", "C-Scheme")
        ),
        CoverageCity(
            id = "indore",
            name = "Indore",
            nameHindi = "इंदौर",
            region = RegionType.ALL_INDIA,
            localities = listOf("Vijay Nagar", "Palasia", "Bhawarkua", "Rajwada")
        )
    )
}
