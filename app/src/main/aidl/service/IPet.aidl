// IPet.aidl
package service;
import service.Pet;
import service.Person;

// Declare any non-default types here with import statements

interface IPet {
   //定义一个Person对象作为传入参数
   List<Pet> getPets(in Person owner);
}
