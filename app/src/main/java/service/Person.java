package service;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ysy on 2015/3/19.
 */
public class Person implements Parcelable {
    private Integer id;
    private String name;
    private String pass;

    public Person() {

    }

    public Person(Integer id, String name, String pass) {
        super();
        this.id = id;
        this.name = name;
        this.pass = pass;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((pass == null) ? 0 : pass.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        Person other = (Person) o;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (pass == null) {
            if (other.pass != null) {
                return false;
            }
        } else if (!pass.equals(other.pass)) {
            return false;
        }

        return true;

    }

    //实现Parcelable接口必须实现的方法
    @Override
    public int describeContents() {
        return 0;
    }

    //实现Parcelable接口必须实现的方法
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //把该对象包含的数据写到Parcel
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(pass);
    }

    //添加一个静态成员，名为CREATOR，该对象实现了Parcelable.Creator接口
    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel source) {
            //从Parcel中兑取数据，返回Person对象
            return new Person(source.readInt(), source.readString(), source.readString());
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}
