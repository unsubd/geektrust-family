package com.aditapillai.projects.geektrustfamily.utils;

import com.aditapillai.projects.geektrustfamily.constants.Gender;
import com.aditapillai.projects.geektrustfamily.family.Families;
import com.aditapillai.projects.geektrustfamily.family.Family;
import com.aditapillai.projects.geektrustfamily.family.Person;

public class IOUtils {
    private static final String INIT_FILE_PATH = "init/family.txt";

    //    Jaya(M)==Dritha(F)[Yodhan(M)]
    public static Family initializeFamily() {
        Person kingShan = Person.builder()
                                .name("King Shan")
                                .gender(Gender.M)
                                .build();

        Family family = Families.createFamily(kingShan);
//        King_Shan(M)==Queen_Anga(F)[Chit(M),Ish(M),Vich(M),Aras(M),Satya(F)]
        family.hostWedding(kingShan.name, "Queen Anga");
        family.addChild("Queen Anga", "Chit", Gender.M);
        family.addChild("Queen Anga", "Ish", Gender.M);
        family.addChild("Queen Anga", "Vich", Gender.M);
        family.addChild("Queen Anga", "Aras", Gender.M);
        family.addChild("Queen Anga", "Satya", Gender.F);
//        Chit(M)==Amba(F)[Dritha(F),Tritha(F),Vritha(M)]
        family.hostWedding("Chit", "Amba");
        family.addChild("Amba", "Dritha", Gender.F);
        family.addChild("Amba", "Tritha", Gender.F);
        family.addChild("Amba", "Vritha", Gender.M);
//        Jaya(M)==Dritha(F)[Yodhan(M)]
        family.hostWedding("Jaya", "Dritha");
        family.addChild("Dritha", "Yodhan", Gender.M);

//        Vich(M)==Lika(F)[Vila(F),Chika(F)]
        family.hostWedding("Vich", "Lika");
        family.addChild("Lika", "Vila", Gender.F);
        family.addChild("Lika", "Chika", Gender.F);
//        Aras(M)==Chitra(F)[Ahit(M),Jnki(F)]
        family.hostWedding("Aras", "Chitra");
        family.addChild("Chitra", "Ahit", Gender.M);
        family.addChild("Chitra", "Jnki", Gender.F);
//        Arit(M)==Jnki(F)[Laki(M),Lavnya(F)]
        family.hostWedding("Arit", "Jnki");
        family.addChild("Jnki", "Laki", Gender.M);
        family.addChild("Jnki", "Lavnya", Gender.F);

//        Vyan(M)==Stya(F)[Asva(M),Vyas(M),Atya(F)]
        family.hostWedding("Vyan", "Satya");
        family.addChild("Satya", "Asva", Gender.M);
        family.addChild("Satya", "Vyas", Gender.M);
        family.addChild("Satya", "Atya", Gender.F);

//        Asva(M)==Satvy(F)[Vasa(M)]
        family.hostWedding("Asva", "Satvy");
        family.addChild("Satvy", "Vasa", Gender.M);
//        Vyas(M)==Krpi(F)[Kriya(M),Krithi(F)]
        family.hostWedding("Vyas", "Krpi");
        family.addChild("Krpi", "Kriya", Gender.M);
        family.addChild("Krpi", "Krithi", Gender.F);

        return family;
    }

}
