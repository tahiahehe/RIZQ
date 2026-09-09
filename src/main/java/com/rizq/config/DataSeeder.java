package com.rizq.config;

import com.rizq.model.*;
import com.rizq.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/** Seeds demo accounts, NGOs and donations on first run. */
@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository users;
    private final NgoRepository ngos;
    private final DonationRepository donations;
    private final PasswordEncoder encoder;

    public DataSeeder(UserRepository users, NgoRepository ngos,
                      DonationRepository donations, PasswordEncoder encoder) {
        this.users = users;
        this.ngos = ngos;
        this.donations = donations;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        if (users.count() > 0) return;

        User admin = user("Rizq Admin", "admin@rizq.com", "admin123", Role.ADMIN, "Dhaka", null);
        User donor = user("Tanvir Ahmed", "donor@rizq.com", "donor123", Role.DONOR, "Dhaka", null);
        User ngoUser = user("Bidyanondo Foundation", "ngo@rizq.com", "ngo123", Role.NGO, "Dhaka", "Bidyanondo Foundation");
        User donor2 = user("Kacchi Bhai", "kacchi@rizq.com", "donor123", Role.DONOR, "Dhaka", "Kacchi Bhai");

        ngos.save(ngo("BRAC", "Dhaka", "Founded in 1972, BRAC is one of the world's largest NGOs, running nationwide food security, nutrition and emergency relief programmes from its Mohakhali head office.", "Food Security & Relief", "+880 2-2222-81265", 152000,
                "/images/photo-1488521787991-ed7bbaae773c.jpg"));
        ngos.save(ngo("Bidyanondo Foundation", "Dhaka", "Volunteer-run charity founded in 2013, best known for its 'One Taka Meal' kitchens, orphanages and disaster-time cooked-food distribution.", "One Taka Meal Kitchens", "+880 1329-670103", 98000,
                "/images/photo-1593113646773-028c64a8f1b8.jpg"));
        ngos.save(ngo("As-Sunnah Foundation", "Dhaka", "Established in 2017, As-Sunnah runs zakat-funded food packages, winter relief and orphan sponsorship across all divisions of Bangladesh.", "Zakat Food Packages", "+880 9612-316316", 76000,
                "/images/photo-1509099836639-18ba1795216d.jpg"));
        ngos.save(ngo("Anjuman Mufidul Islam", "Dhaka", "Bangladesh's oldest welfare body (est. 1905) serving orphans and the destitute with meals, shelter, ambulance and burial services.", "Orphan & Destitute Care", "+880 2-9557282", 64000,
                "/images/photo-1518398046578-8cca57782e17.jpg"));
        ngos.save(ngo("JAAGO Foundation", "Dhaka", "Education-focused NGO founded in 2007 in Rayer Bazar, providing school meals and community nutrition support for underprivileged children.", "School Meals & Nutrition", "+880 1730-045673", 45000,
                "/images/photo-1469571486292-0ba58a3f068b.jpg"));
        ngos.save(ngo("Ahsania Mission (DAM)", "Dhaka", "Dhaka Ahsania Mission, working since 1958 on health, education and food assistance for low-income urban and rural families.", "Community Food Support", "+880 2-8119521", 38000,
                "/images/photo-1532629345422-7515f3d16bb6.jpg"));
        ngos.save(ngo("YPSA", "Chattogram", "Young Power in Social Action, a Chattogram-based development organisation running nutrition and relief programmes in the port city and Cox's Bazar.", "Nutrition & Relief", "+880 31-2570255", 29000,
                "/images/photo-1488521787991-ed7bbaae773c.jpg"));
        ngos.save(ngo("Ekti Bari Ekti Khamar Trust", "Sylhet", "Community trust supporting rural households in Sylhet division with food distribution and household-level nutrition support.", "Rural Food Distribution", "+880 821-761234", 21000,
                "/images/photo-1509440159596-0249088772ff.jpg"));

        donations.save(donation("Kacchi Biryani — 30 plates",
                "Surplus kacchi from a cancelled reception at Kacchi Bhai Gulshan. Packed in foil boxes, cooked this afternoon.",
                "Cooked Meal", 30, "Dhaka", "Gulshan", "Kacchi Bhai, Road 11, Gulshan-1", donor2,
                "/images/photo-1512058564366-18510be2db19.jpg"));
        donations.save(donation("Cooper's Bakery End-of-Day Items",
                "Breads, buns, patties and pastries unsold at closing from the Dhanmondi outlet.",
                "Bakery", 50, "Dhaka", "Dhanmondi", "Cooper's, Satmasjid Road, Dhanmondi", donor,
                "/images/photo-1509440159596-0249088772ff.jpg"));
        donations.save(donation("Shwapno Rice & Lentil Sacks",
                "Sealed 10kg Miniket rice and 5kg masoor dal near the shelf-rotation date at Shwapno Agrabad.",
                "Groceries", 40, "Chattogram", "Agrabad", "Shwapno Outlet, Agrabad Commercial Area", donor,
                "/images/photo-1542838132-92c53300491e.jpg"));
        donations.save(donation("Seasonal Fruit Crates",
                "Bananas, papaya and guava surplus from the Zindabazar wholesale market.",
                "Fruits", 25, "Sylhet", "Zindabazar", "Kalighat Fruit Market, Zindabazar", donor,
                "/images/photo-1610832958506-aa56368176cf.jpg"));
        donations.save(donation("Sultan's Dine Wedding Surplus",
                "Roast, polao and borhani from a wedding at Sultan's Dine Banani. Kept hot, pickup within 3 hours.",
                "Cooked Meal", 80, "Dhaka", "Banani", "Sultan's Dine, Kemal Ataturk Avenue, Banani", donor2,
                "/images/photo-1555244162-803834f70033.jpg"));
        donations.save(donation("Meena Bazar Packaged Meal Kits",
                "Ready-to-eat khichuri and dal kits, factory sealed, three weeks before expiry.",
                "Packaged", 60, "Dhaka", "Mirpur", "Meena Bazar, Mirpur-10", donor,
                "/images/photo-1546069901-ba9599a7e63c.jpg"));
        donations.save(donation("Hotel Agrabad Breakfast Buffet",
                "Untouched breakfast buffet items — parathas, eggs, sausages and fruit — from Hotel Agrabad.",
                "Cooked Meal", 45, "Chattogram", "Khulshi", "Hotel Agrabad, Sheikh Mujib Road", donor2,
                "/images/photo-1533089860892-a7c6f0a88666.jpg"));
        donations.save(donation("Panshi Restaurant Fish Curry & Rice",
                "Traditional Sylheti fish curry with steamed rice, prepared today at Panshi Zindabazar.",
                "Cooked Meal", 35, "Sylhet", "Zindabazar", "Panshi Restaurant, Zindabazar", donor,
                "/images/photo-1547592180-85f173990554.jpg"));
    }

    private User user(String name, String email, String password, Role role, String city, String org) {
        User u = new User();
        u.setFullName(name);
        u.setEmail(email);
        u.setPassword(encoder.encode(password));
        u.setRole(role);
        u.setCity(city);
        u.setOrganization(org);
        u.setPhone("+880 1700-000000");
        u.setVerified(true);
        return users.save(u);
    }

    private Ngo ngo(String name, String city, String desc, String focus, String phone, int meals, String logoUrl) {
        Ngo n = new Ngo();
        n.setName(name);
        n.setCity(city);
        n.setDescription(desc);
        n.setFocusArea(focus);
        n.setPhone(phone);
        n.setEmail(name.toLowerCase().replaceAll("[^a-z]", "") + "@example.org");
        n.setVerified(true);
        n.setMealsDistributed(meals);
        n.setLogoUrl(logoUrl);
        return n;
    }

    private Donation donation(String title, String desc, String category, int servings,
                              String city, String area, String address, User donor, String imageUrl) {
        Donation d = new Donation();
        d.setTitle(title);
        d.setDescription(desc);
        d.setCategory(category);
        d.setServings(servings);
        d.setCity(city);
        d.setArea(area);
        d.setPickupAddress(address);
        d.setContactPhone("+880 1700-000000");
        d.setPickupBefore(LocalDateTime.now().plusDays(1));
        d.setStatus(DonationStatus.AVAILABLE);
        d.setDonor(donor);
        d.setImageUrl(imageUrl);
        return d;
    }
}
