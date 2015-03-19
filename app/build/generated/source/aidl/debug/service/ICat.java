/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\AndroidStudioProjects\\Demo\\app\\src\\main\\aidl\\service\\ICat.aidl
 */
package service;
// Declare any non-default types here with import statements

public interface ICat extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements service.ICat
{
private static final java.lang.String DESCRIPTOR = "service.ICat";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an service.ICat interface,
 * generating a proxy if needed.
 */
public static service.ICat asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof service.ICat))) {
return ((service.ICat)iin);
}
return new service.ICat.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_getCorlor:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _result = this.getCorlor();
reply.writeNoException();
reply.writeString(_result);
return true;
}
case TRANSACTION_getWeight:
{
data.enforceInterface(DESCRIPTOR);
double _result = this.getWeight();
reply.writeNoException();
reply.writeDouble(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements service.ICat
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
/**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
@Override public java.lang.String getCorlor() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getCorlor, _data, _reply, 0);
_reply.readException();
_result = _reply.readString();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public double getWeight() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
double _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getWeight, _data, _reply, 0);
_reply.readException();
_result = _reply.readDouble();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_getCorlor = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_getWeight = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}
/**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
public java.lang.String getCorlor() throws android.os.RemoteException;
public double getWeight() throws android.os.RemoteException;
}
