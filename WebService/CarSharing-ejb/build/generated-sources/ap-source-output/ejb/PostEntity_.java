package ejb;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-04-02T19:34:45")
@StaticMetamodel(PostEntity.class)
public class PostEntity_ { 

    public static volatile SingularAttribute<PostEntity, Long> id;
    public static volatile SingularAttribute<PostEntity, String> carType;
    public static volatile SingularAttribute<PostEntity, String> username;
    public static volatile SingularAttribute<PostEntity, String> postType;
    public static volatile SingularAttribute<PostEntity, String> departure;
    public static volatile SingularAttribute<PostEntity, String> carYear;
    public static volatile SingularAttribute<PostEntity, String> date;
    public static volatile SingularAttribute<PostEntity, String> destination;

}