package ksoft.seat_management_pb;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.50.2)",
    comments = "Source: ksoft/seat_management_pb/seat_management.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class SeatManagementServiceGrpc {

  private SeatManagementServiceGrpc() {}

  public static final String SERVICE_NAME = "ksoft.seat_management_pb.SeatManagementService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<ksoft.seat_management_pb.SeatManagement.GetUserStatusRequest,
      ksoft.seat_management_pb.SeatManagement.GetUserStatusResponse> getGetUserStatusMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetUserStatus",
      requestType = ksoft.seat_management_pb.SeatManagement.GetUserStatusRequest.class,
      responseType = ksoft.seat_management_pb.SeatManagement.GetUserStatusResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ksoft.seat_management_pb.SeatManagement.GetUserStatusRequest,
      ksoft.seat_management_pb.SeatManagement.GetUserStatusResponse> getGetUserStatusMethod() {
    io.grpc.MethodDescriptor<ksoft.seat_management_pb.SeatManagement.GetUserStatusRequest, ksoft.seat_management_pb.SeatManagement.GetUserStatusResponse> getGetUserStatusMethod;
    if ((getGetUserStatusMethod = SeatManagementServiceGrpc.getGetUserStatusMethod) == null) {
      synchronized (SeatManagementServiceGrpc.class) {
        if ((getGetUserStatusMethod = SeatManagementServiceGrpc.getGetUserStatusMethod) == null) {
          SeatManagementServiceGrpc.getGetUserStatusMethod = getGetUserStatusMethod =
              io.grpc.MethodDescriptor.<ksoft.seat_management_pb.SeatManagement.GetUserStatusRequest, ksoft.seat_management_pb.SeatManagement.GetUserStatusResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetUserStatus"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.seat_management_pb.SeatManagement.GetUserStatusRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.seat_management_pb.SeatManagement.GetUserStatusResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SeatManagementServiceMethodDescriptorSupplier("GetUserStatus"))
              .build();
        }
      }
    }
    return getGetUserStatusMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ksoft.seat_management_pb.SeatManagement.RegisterUserRequest,
      ksoft.seat_management_pb.SeatManagement.RegisterUserResponse> getRegisterUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RegisterUser",
      requestType = ksoft.seat_management_pb.SeatManagement.RegisterUserRequest.class,
      responseType = ksoft.seat_management_pb.SeatManagement.RegisterUserResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ksoft.seat_management_pb.SeatManagement.RegisterUserRequest,
      ksoft.seat_management_pb.SeatManagement.RegisterUserResponse> getRegisterUserMethod() {
    io.grpc.MethodDescriptor<ksoft.seat_management_pb.SeatManagement.RegisterUserRequest, ksoft.seat_management_pb.SeatManagement.RegisterUserResponse> getRegisterUserMethod;
    if ((getRegisterUserMethod = SeatManagementServiceGrpc.getRegisterUserMethod) == null) {
      synchronized (SeatManagementServiceGrpc.class) {
        if ((getRegisterUserMethod = SeatManagementServiceGrpc.getRegisterUserMethod) == null) {
          SeatManagementServiceGrpc.getRegisterUserMethod = getRegisterUserMethod =
              io.grpc.MethodDescriptor.<ksoft.seat_management_pb.SeatManagement.RegisterUserRequest, ksoft.seat_management_pb.SeatManagement.RegisterUserResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RegisterUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.seat_management_pb.SeatManagement.RegisterUserRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.seat_management_pb.SeatManagement.RegisterUserResponse.getDefaultInstance()))
              .setSchemaDescriptor(new SeatManagementServiceMethodDescriptorSupplier("RegisterUser"))
              .build();
        }
      }
    }
    return getRegisterUserMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static SeatManagementServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SeatManagementServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SeatManagementServiceStub>() {
        @java.lang.Override
        public SeatManagementServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SeatManagementServiceStub(channel, callOptions);
        }
      };
    return SeatManagementServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static SeatManagementServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SeatManagementServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SeatManagementServiceBlockingStub>() {
        @java.lang.Override
        public SeatManagementServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SeatManagementServiceBlockingStub(channel, callOptions);
        }
      };
    return SeatManagementServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static SeatManagementServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<SeatManagementServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<SeatManagementServiceFutureStub>() {
        @java.lang.Override
        public SeatManagementServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new SeatManagementServiceFutureStub(channel, callOptions);
        }
      };
    return SeatManagementServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class SeatManagementServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getUserStatus(ksoft.seat_management_pb.SeatManagement.GetUserStatusRequest request,
        io.grpc.stub.StreamObserver<ksoft.seat_management_pb.SeatManagement.GetUserStatusResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetUserStatusMethod(), responseObserver);
    }

    /**
     */
    public void registerUser(ksoft.seat_management_pb.SeatManagement.RegisterUserRequest request,
        io.grpc.stub.StreamObserver<ksoft.seat_management_pb.SeatManagement.RegisterUserResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRegisterUserMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetUserStatusMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                ksoft.seat_management_pb.SeatManagement.GetUserStatusRequest,
                ksoft.seat_management_pb.SeatManagement.GetUserStatusResponse>(
                  this, METHODID_GET_USER_STATUS)))
          .addMethod(
            getRegisterUserMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                ksoft.seat_management_pb.SeatManagement.RegisterUserRequest,
                ksoft.seat_management_pb.SeatManagement.RegisterUserResponse>(
                  this, METHODID_REGISTER_USER)))
          .build();
    }
  }

  /**
   */
  public static final class SeatManagementServiceStub extends io.grpc.stub.AbstractAsyncStub<SeatManagementServiceStub> {
    private SeatManagementServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SeatManagementServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SeatManagementServiceStub(channel, callOptions);
    }

    /**
     */
    public void getUserStatus(ksoft.seat_management_pb.SeatManagement.GetUserStatusRequest request,
        io.grpc.stub.StreamObserver<ksoft.seat_management_pb.SeatManagement.GetUserStatusResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetUserStatusMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void registerUser(ksoft.seat_management_pb.SeatManagement.RegisterUserRequest request,
        io.grpc.stub.StreamObserver<ksoft.seat_management_pb.SeatManagement.RegisterUserResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRegisterUserMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class SeatManagementServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<SeatManagementServiceBlockingStub> {
    private SeatManagementServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SeatManagementServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SeatManagementServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public ksoft.seat_management_pb.SeatManagement.GetUserStatusResponse getUserStatus(ksoft.seat_management_pb.SeatManagement.GetUserStatusRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetUserStatusMethod(), getCallOptions(), request);
    }

    /**
     */
    public ksoft.seat_management_pb.SeatManagement.RegisterUserResponse registerUser(ksoft.seat_management_pb.SeatManagement.RegisterUserRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegisterUserMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class SeatManagementServiceFutureStub extends io.grpc.stub.AbstractFutureStub<SeatManagementServiceFutureStub> {
    private SeatManagementServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected SeatManagementServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new SeatManagementServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ksoft.seat_management_pb.SeatManagement.GetUserStatusResponse> getUserStatus(
        ksoft.seat_management_pb.SeatManagement.GetUserStatusRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetUserStatusMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ksoft.seat_management_pb.SeatManagement.RegisterUserResponse> registerUser(
        ksoft.seat_management_pb.SeatManagement.RegisterUserRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRegisterUserMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_USER_STATUS = 0;
  private static final int METHODID_REGISTER_USER = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final SeatManagementServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(SeatManagementServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_USER_STATUS:
          serviceImpl.getUserStatus((ksoft.seat_management_pb.SeatManagement.GetUserStatusRequest) request,
              (io.grpc.stub.StreamObserver<ksoft.seat_management_pb.SeatManagement.GetUserStatusResponse>) responseObserver);
          break;
        case METHODID_REGISTER_USER:
          serviceImpl.registerUser((ksoft.seat_management_pb.SeatManagement.RegisterUserRequest) request,
              (io.grpc.stub.StreamObserver<ksoft.seat_management_pb.SeatManagement.RegisterUserResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class SeatManagementServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    SeatManagementServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return ksoft.seat_management_pb.SeatManagement.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("SeatManagementService");
    }
  }

  private static final class SeatManagementServiceFileDescriptorSupplier
      extends SeatManagementServiceBaseDescriptorSupplier {
    SeatManagementServiceFileDescriptorSupplier() {}
  }

  private static final class SeatManagementServiceMethodDescriptorSupplier
      extends SeatManagementServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    SeatManagementServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (SeatManagementServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new SeatManagementServiceFileDescriptorSupplier())
              .addMethod(getGetUserStatusMethod())
              .addMethod(getRegisterUserMethod())
              .build();
        }
      }
    }
    return result;
  }
}
