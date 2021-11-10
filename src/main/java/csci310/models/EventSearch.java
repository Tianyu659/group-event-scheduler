package csci310.models;

import java.util.StringJoiner;

public class EventSearch {
    public enum Parameter {
        id,keyword,attractionId,venueId,postalCode,latLong,
        radius,unit,source,locale,marketId,startDateTime,
        endDateTime,includeTBA,includeTBD,includeTest,size,
        page,sort,onsaleStartDateTime,onsaleEndDateTime,city,
        countryCode,stateCode,classificationName,
        classificationId,dmaId,localStartDateTime,
        localStartEndDateTime,startEndDateTime,
        publicVisibilityStartDateTime,preSaleDateTime,
        onsaleOnStartDate,onsaleOnAfterStartDate,collectionId,
        segmentId,segmentName,includeFamily,promoterId,genreId,
        subGenreId,typeId,subTypeId,geoPoint,preferredCountry,
        includeSpellcheck,domain;
    
        @Override public String toString() {return name();}
    
    }
    static String sanitize(String in) {
        return (in = in.trim().replaceAll(" ","+")).isEmpty()? null : in;
    }
    static String array(final String...strings) {
        final StringJoiner sj = new StringJoiner(",");
        for(String s : strings)
            if(s != null && !(s = s.trim()).isEmpty())
                sj.add(s);
        return sj.toString();
    }
    
    final String[] data = new String[Parameter.values().length];
    public void put(final Parameter param,final String...values) {
        String v = null;
        if(values != null && values.length != 0) {
            if(values.length > 1) v = array(values);
            else v = values[0];
            v = sanitize(v);
        }
        data[param.ordinal()] = v;
    }
    
    @Override
    public String toString() {
        final StringBuilder sj = new StringBuilder();
        for(final Parameter p : Parameter.values()) {
            final String v = data[p.ordinal()];
            if(v != null)
                sj.append('&')
                  .append(p.name())
                  .append('=')
                  .append(v);
        }
        return sj.toString();
    }
}