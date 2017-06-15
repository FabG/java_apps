package com.circleback.utility;

import java.util.ArrayList;
import java.util.List;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.MXRecord;
import org.xbill.DNS.Record;
import org.xbill.DNS.SRVRecord;
import org.xbill.DNS.TextParseException;
import org.xbill.DNS.Type;

/**
 * Created by Fab on 6/15/17.
 */
public class MXLookup {

    /* Method to check what Email Server is hosting an email domain: Google (GoogleApps+gmail), Exchange Server, Exchange Online */
    public String checkMXRecords(String domain) {

        List<String> targets = new ArrayList<String>();

        try {
            Record[] mxRecords = new Lookup(domain, Type.MX).run();
            if (mxRecords != null)
                for (Record mxRecord : mxRecords) {
                    MXRecord mx = (MXRecord) mxRecord;
                    targets.add(mx.getTarget().toString());
                }

            Record[] srvRecords = new Lookup("_autodiscover._tcp." + domain, Type.SRV).run();
            if (srvRecords != null)
                for (Record srvRecord : srvRecords) {
                    SRVRecord srv = (SRVRecord) srvRecord;
                    targets.add(srv.getTarget().toString());
                }
            else {
                // For Exchange, see if there is a Cname for autodiscover.<domain>
                Record[] cNameRecords = new Lookup("autodiscover." + domain, Type.CNAME).run();
                if (cNameRecords != null)
                    targets.add("autodiscover" + domain);
            }


        } catch (TextParseException e) {
            e.printStackTrace();
        }

        if (targets != null && targets.size() > 0)
            for (String target : targets)
                if (target.toLowerCase().contains("googlemail.com") || target.toLowerCase().contains("aspmx.l.google.com") || target.toLowerCase().contains("smtp-in.l.google.com"))
                    return "Google";
                else if (target.toLowerCase().contains("protection.outlook.com") || target.toLowerCase().contains("hotmail.com") || target.toLowerCase().contains("microsoftonline.com"))
                    return "MicrosoftOnline";
                else if (target.toLowerCase().contains("exch1") || target.toLowerCase().contains("exch2") || target.toLowerCase().contains("autodiscover"+ domain))
                    return "MicrosoftExchange";

        return "Unknown";

    }
}
