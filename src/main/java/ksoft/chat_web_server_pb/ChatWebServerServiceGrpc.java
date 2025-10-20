package ksoft.chat_web_server_pb;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.50.2)",
    comments = "Source: ksoft/chat_web_server_pb/chat_web_server.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ChatWebServerServiceGrpc {

  private ChatWebServerServiceGrpc() {}

  public static final String SERVICE_NAME = "ksoft.chat_web_server_pb.ChatWebServerService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<ksoft.chat_web_server_pb.ChatWebServer.GetMatchingIndexedReposRequest,
      ksoft.chat_web_server_pb.ChatWebServer.GetMatchingIndexedReposResponse> getGetMatchingIndexedReposMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetMatchingIndexedRepos",
      requestType = ksoft.chat_web_server_pb.ChatWebServer.GetMatchingIndexedReposRequest.class,
      responseType = ksoft.chat_web_server_pb.ChatWebServer.GetMatchingIndexedReposResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ksoft.chat_web_server_pb.ChatWebServer.GetMatchingIndexedReposRequest,
      ksoft.chat_web_server_pb.ChatWebServer.GetMatchingIndexedReposResponse> getGetMatchingIndexedReposMethod() {
    io.grpc.MethodDescriptor<ksoft.chat_web_server_pb.ChatWebServer.GetMatchingIndexedReposRequest, ksoft.chat_web_server_pb.ChatWebServer.GetMatchingIndexedReposResponse> getGetMatchingIndexedReposMethod;
    if ((getGetMatchingIndexedReposMethod = ChatWebServerServiceGrpc.getGetMatchingIndexedReposMethod) == null) {
      synchronized (ChatWebServerServiceGrpc.class) {
        if ((getGetMatchingIndexedReposMethod = ChatWebServerServiceGrpc.getGetMatchingIndexedReposMethod) == null) {
          ChatWebServerServiceGrpc.getGetMatchingIndexedReposMethod = getGetMatchingIndexedReposMethod =
              io.grpc.MethodDescriptor.<ksoft.chat_web_server_pb.ChatWebServer.GetMatchingIndexedReposRequest, ksoft.chat_web_server_pb.ChatWebServer.GetMatchingIndexedReposResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetMatchingIndexedRepos"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.chat_web_server_pb.ChatWebServer.GetMatchingIndexedReposRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.chat_web_server_pb.ChatWebServer.GetMatchingIndexedReposResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ChatWebServerServiceMethodDescriptorSupplier("GetMatchingIndexedRepos"))
              .build();
        }
      }
    }
    return getGetMatchingIndexedReposMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ksoft.chat_web_server_pb.ChatWebServer.GetMatchingCodeContextRequest,
      ksoft.chat_web_server_pb.ChatWebServer.GetMatchingCodeContextResponse> getGetMatchingCodeContextMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetMatchingCodeContext",
      requestType = ksoft.chat_web_server_pb.ChatWebServer.GetMatchingCodeContextRequest.class,
      responseType = ksoft.chat_web_server_pb.ChatWebServer.GetMatchingCodeContextResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ksoft.chat_web_server_pb.ChatWebServer.GetMatchingCodeContextRequest,
      ksoft.chat_web_server_pb.ChatWebServer.GetMatchingCodeContextResponse> getGetMatchingCodeContextMethod() {
    io.grpc.MethodDescriptor<ksoft.chat_web_server_pb.ChatWebServer.GetMatchingCodeContextRequest, ksoft.chat_web_server_pb.ChatWebServer.GetMatchingCodeContextResponse> getGetMatchingCodeContextMethod;
    if ((getGetMatchingCodeContextMethod = ChatWebServerServiceGrpc.getGetMatchingCodeContextMethod) == null) {
      synchronized (ChatWebServerServiceGrpc.class) {
        if ((getGetMatchingCodeContextMethod = ChatWebServerServiceGrpc.getGetMatchingCodeContextMethod) == null) {
          ChatWebServerServiceGrpc.getGetMatchingCodeContextMethod = getGetMatchingCodeContextMethod =
              io.grpc.MethodDescriptor.<ksoft.chat_web_server_pb.ChatWebServer.GetMatchingCodeContextRequest, ksoft.chat_web_server_pb.ChatWebServer.GetMatchingCodeContextResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetMatchingCodeContext"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.chat_web_server_pb.ChatWebServer.GetMatchingCodeContextRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.chat_web_server_pb.ChatWebServer.GetMatchingCodeContextResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ChatWebServerServiceMethodDescriptorSupplier("GetMatchingCodeContext"))
              .build();
        }
      }
    }
    return getGetMatchingCodeContextMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ChatWebServerServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChatWebServerServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChatWebServerServiceStub>() {
        @java.lang.Override
        public ChatWebServerServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChatWebServerServiceStub(channel, callOptions);
        }
      };
    return ChatWebServerServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ChatWebServerServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChatWebServerServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChatWebServerServiceBlockingStub>() {
        @java.lang.Override
        public ChatWebServerServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChatWebServerServiceBlockingStub(channel, callOptions);
        }
      };
    return ChatWebServerServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ChatWebServerServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ChatWebServerServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ChatWebServerServiceFutureStub>() {
        @java.lang.Override
        public ChatWebServerServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ChatWebServerServiceFutureStub(channel, callOptions);
        }
      };
    return ChatWebServerServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ChatWebServerServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getMatchingIndexedRepos(ksoft.chat_web_server_pb.ChatWebServer.GetMatchingIndexedReposRequest request,
        io.grpc.stub.StreamObserver<ksoft.chat_web_server_pb.ChatWebServer.GetMatchingIndexedReposResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetMatchingIndexedReposMethod(), responseObserver);
    }

    /**
     */
    public void getMatchingCodeContext(ksoft.chat_web_server_pb.ChatWebServer.GetMatchingCodeContextRequest request,
        io.grpc.stub.StreamObserver<ksoft.chat_web_server_pb.ChatWebServer.GetMatchingCodeContextResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetMatchingCodeContextMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetMatchingIndexedReposMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                ksoft.chat_web_server_pb.ChatWebServer.GetMatchingIndexedReposRequest,
                ksoft.chat_web_server_pb.ChatWebServer.GetMatchingIndexedReposResponse>(
                  this, METHODID_GET_MATCHING_INDEXED_REPOS)))
          .addMethod(
            getGetMatchingCodeContextMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                ksoft.chat_web_server_pb.ChatWebServer.GetMatchingCodeContextRequest,
                ksoft.chat_web_server_pb.ChatWebServer.GetMatchingCodeContextResponse>(
                  this, METHODID_GET_MATCHING_CODE_CONTEXT)))
          .build();
    }
  }

  /**
   */
  public static final class ChatWebServerServiceStub extends io.grpc.stub.AbstractAsyncStub<ChatWebServerServiceStub> {
    private ChatWebServerServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatWebServerServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChatWebServerServiceStub(channel, callOptions);
    }

    /**
     */
    public void getMatchingIndexedRepos(ksoft.chat_web_server_pb.ChatWebServer.GetMatchingIndexedReposRequest request,
        io.grpc.stub.StreamObserver<ksoft.chat_web_server_pb.ChatWebServer.GetMatchingIndexedReposResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetMatchingIndexedReposMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getMatchingCodeContext(ksoft.chat_web_server_pb.ChatWebServer.GetMatchingCodeContextRequest request,
        io.grpc.stub.StreamObserver<ksoft.chat_web_server_pb.ChatWebServer.GetMatchingCodeContextResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetMatchingCodeContextMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ChatWebServerServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<ChatWebServerServiceBlockingStub> {
    private ChatWebServerServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatWebServerServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChatWebServerServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public ksoft.chat_web_server_pb.ChatWebServer.GetMatchingIndexedReposResponse getMatchingIndexedRepos(ksoft.chat_web_server_pb.ChatWebServer.GetMatchingIndexedReposRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetMatchingIndexedReposMethod(), getCallOptions(), request);
    }

    /**
     */
    public ksoft.chat_web_server_pb.ChatWebServer.GetMatchingCodeContextResponse getMatchingCodeContext(ksoft.chat_web_server_pb.ChatWebServer.GetMatchingCodeContextRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetMatchingCodeContextMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ChatWebServerServiceFutureStub extends io.grpc.stub.AbstractFutureStub<ChatWebServerServiceFutureStub> {
    private ChatWebServerServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ChatWebServerServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ChatWebServerServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ksoft.chat_web_server_pb.ChatWebServer.GetMatchingIndexedReposResponse> getMatchingIndexedRepos(
        ksoft.chat_web_server_pb.ChatWebServer.GetMatchingIndexedReposRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetMatchingIndexedReposMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ksoft.chat_web_server_pb.ChatWebServer.GetMatchingCodeContextResponse> getMatchingCodeContext(
        ksoft.chat_web_server_pb.ChatWebServer.GetMatchingCodeContextRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetMatchingCodeContextMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_MATCHING_INDEXED_REPOS = 0;
  private static final int METHODID_GET_MATCHING_CODE_CONTEXT = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ChatWebServerServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ChatWebServerServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_MATCHING_INDEXED_REPOS:
          serviceImpl.getMatchingIndexedRepos((ksoft.chat_web_server_pb.ChatWebServer.GetMatchingIndexedReposRequest) request,
              (io.grpc.stub.StreamObserver<ksoft.chat_web_server_pb.ChatWebServer.GetMatchingIndexedReposResponse>) responseObserver);
          break;
        case METHODID_GET_MATCHING_CODE_CONTEXT:
          serviceImpl.getMatchingCodeContext((ksoft.chat_web_server_pb.ChatWebServer.GetMatchingCodeContextRequest) request,
              (io.grpc.stub.StreamObserver<ksoft.chat_web_server_pb.ChatWebServer.GetMatchingCodeContextResponse>) responseObserver);
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

  private static abstract class ChatWebServerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ChatWebServerServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return ksoft.chat_web_server_pb.ChatWebServer.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ChatWebServerService");
    }
  }

  private static final class ChatWebServerServiceFileDescriptorSupplier
      extends ChatWebServerServiceBaseDescriptorSupplier {
    ChatWebServerServiceFileDescriptorSupplier() {}
  }

  private static final class ChatWebServerServiceMethodDescriptorSupplier
      extends ChatWebServerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ChatWebServerServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (ChatWebServerServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ChatWebServerServiceFileDescriptorSupplier())
              .addMethod(getGetMatchingIndexedReposMethod())
              .addMethod(getGetMatchingCodeContextMethod())
              .build();
        }
      }
    }
    return result;
  }
}
