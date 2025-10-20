package ksoft.language_server_pb;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.50.2)",
    comments = "Source: ksoft/language_server/language_server.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class LanguageServerServiceGrpc {

  private LanguageServerServiceGrpc() {}

  public static final String SERVICE_NAME = "ksoft.language_server_pb.LanguageServerService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.GetCompletionsRequest,
      ksoft.language_server_pb.LanguageServer.GetCompletionsResponse> getGetCompletionsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetCompletions",
      requestType = ksoft.language_server_pb.LanguageServer.GetCompletionsRequest.class,
      responseType = ksoft.language_server_pb.LanguageServer.GetCompletionsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.GetCompletionsRequest,
      ksoft.language_server_pb.LanguageServer.GetCompletionsResponse> getGetCompletionsMethod() {
    io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.GetCompletionsRequest, ksoft.language_server_pb.LanguageServer.GetCompletionsResponse> getGetCompletionsMethod;
    if ((getGetCompletionsMethod = LanguageServerServiceGrpc.getGetCompletionsMethod) == null) {
      synchronized (LanguageServerServiceGrpc.class) {
        if ((getGetCompletionsMethod = LanguageServerServiceGrpc.getGetCompletionsMethod) == null) {
          LanguageServerServiceGrpc.getGetCompletionsMethod = getGetCompletionsMethod =
              io.grpc.MethodDescriptor.<ksoft.language_server_pb.LanguageServer.GetCompletionsRequest, ksoft.language_server_pb.LanguageServer.GetCompletionsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetCompletions"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.language_server_pb.LanguageServer.GetCompletionsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.language_server_pb.LanguageServer.GetCompletionsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new LanguageServerServiceMethodDescriptorSupplier("GetCompletions"))
              .build();
        }
      }
    }
    return getGetCompletionsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.AcceptCompletionRequest,
      ksoft.language_server_pb.LanguageServer.AcceptCompletionResponse> getAcceptCompletionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AcceptCompletion",
      requestType = ksoft.language_server_pb.LanguageServer.AcceptCompletionRequest.class,
      responseType = ksoft.language_server_pb.LanguageServer.AcceptCompletionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.AcceptCompletionRequest,
      ksoft.language_server_pb.LanguageServer.AcceptCompletionResponse> getAcceptCompletionMethod() {
    io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.AcceptCompletionRequest, ksoft.language_server_pb.LanguageServer.AcceptCompletionResponse> getAcceptCompletionMethod;
    if ((getAcceptCompletionMethod = LanguageServerServiceGrpc.getAcceptCompletionMethod) == null) {
      synchronized (LanguageServerServiceGrpc.class) {
        if ((getAcceptCompletionMethod = LanguageServerServiceGrpc.getAcceptCompletionMethod) == null) {
          LanguageServerServiceGrpc.getAcceptCompletionMethod = getAcceptCompletionMethod =
              io.grpc.MethodDescriptor.<ksoft.language_server_pb.LanguageServer.AcceptCompletionRequest, ksoft.language_server_pb.LanguageServer.AcceptCompletionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AcceptCompletion"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.language_server_pb.LanguageServer.AcceptCompletionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.language_server_pb.LanguageServer.AcceptCompletionResponse.getDefaultInstance()))
              .setSchemaDescriptor(new LanguageServerServiceMethodDescriptorSupplier("AcceptCompletion"))
              .build();
        }
      }
    }
    return getAcceptCompletionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.HeartbeatRequest,
      ksoft.language_server_pb.LanguageServer.HeartbeatResponse> getHeartbeatMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Heartbeat",
      requestType = ksoft.language_server_pb.LanguageServer.HeartbeatRequest.class,
      responseType = ksoft.language_server_pb.LanguageServer.HeartbeatResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.HeartbeatRequest,
      ksoft.language_server_pb.LanguageServer.HeartbeatResponse> getHeartbeatMethod() {
    io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.HeartbeatRequest, ksoft.language_server_pb.LanguageServer.HeartbeatResponse> getHeartbeatMethod;
    if ((getHeartbeatMethod = LanguageServerServiceGrpc.getHeartbeatMethod) == null) {
      synchronized (LanguageServerServiceGrpc.class) {
        if ((getHeartbeatMethod = LanguageServerServiceGrpc.getHeartbeatMethod) == null) {
          LanguageServerServiceGrpc.getHeartbeatMethod = getHeartbeatMethod =
              io.grpc.MethodDescriptor.<ksoft.language_server_pb.LanguageServer.HeartbeatRequest, ksoft.language_server_pb.LanguageServer.HeartbeatResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Heartbeat"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.language_server_pb.LanguageServer.HeartbeatRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.language_server_pb.LanguageServer.HeartbeatResponse.getDefaultInstance()))
              .setSchemaDescriptor(new LanguageServerServiceMethodDescriptorSupplier("Heartbeat"))
              .build();
        }
      }
    }
    return getHeartbeatMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.GetAuthTokenRequest,
      ksoft.language_server_pb.LanguageServer.GetAuthTokenResponse> getGetAuthTokenMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetAuthToken",
      requestType = ksoft.language_server_pb.LanguageServer.GetAuthTokenRequest.class,
      responseType = ksoft.language_server_pb.LanguageServer.GetAuthTokenResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.GetAuthTokenRequest,
      ksoft.language_server_pb.LanguageServer.GetAuthTokenResponse> getGetAuthTokenMethod() {
    io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.GetAuthTokenRequest, ksoft.language_server_pb.LanguageServer.GetAuthTokenResponse> getGetAuthTokenMethod;
    if ((getGetAuthTokenMethod = LanguageServerServiceGrpc.getGetAuthTokenMethod) == null) {
      synchronized (LanguageServerServiceGrpc.class) {
        if ((getGetAuthTokenMethod = LanguageServerServiceGrpc.getGetAuthTokenMethod) == null) {
          LanguageServerServiceGrpc.getGetAuthTokenMethod = getGetAuthTokenMethod =
              io.grpc.MethodDescriptor.<ksoft.language_server_pb.LanguageServer.GetAuthTokenRequest, ksoft.language_server_pb.LanguageServer.GetAuthTokenResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetAuthToken"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.language_server_pb.LanguageServer.GetAuthTokenRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.language_server_pb.LanguageServer.GetAuthTokenResponse.getDefaultInstance()))
              .setSchemaDescriptor(new LanguageServerServiceMethodDescriptorSupplier("GetAuthToken"))
              .build();
        }
      }
    }
    return getGetAuthTokenMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.RecordEventRequest,
      ksoft.language_server_pb.LanguageServer.RecordEventResponse> getRecordEventMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RecordEvent",
      requestType = ksoft.language_server_pb.LanguageServer.RecordEventRequest.class,
      responseType = ksoft.language_server_pb.LanguageServer.RecordEventResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.RecordEventRequest,
      ksoft.language_server_pb.LanguageServer.RecordEventResponse> getRecordEventMethod() {
    io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.RecordEventRequest, ksoft.language_server_pb.LanguageServer.RecordEventResponse> getRecordEventMethod;
    if ((getRecordEventMethod = LanguageServerServiceGrpc.getRecordEventMethod) == null) {
      synchronized (LanguageServerServiceGrpc.class) {
        if ((getRecordEventMethod = LanguageServerServiceGrpc.getRecordEventMethod) == null) {
          LanguageServerServiceGrpc.getRecordEventMethod = getRecordEventMethod =
              io.grpc.MethodDescriptor.<ksoft.language_server_pb.LanguageServer.RecordEventRequest, ksoft.language_server_pb.LanguageServer.RecordEventResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RecordEvent"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.language_server_pb.LanguageServer.RecordEventRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.language_server_pb.LanguageServer.RecordEventResponse.getDefaultInstance()))
              .setSchemaDescriptor(new LanguageServerServiceMethodDescriptorSupplier("RecordEvent"))
              .build();
        }
      }
    }
    return getRecordEventMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.CancelRequestRequest,
      ksoft.language_server_pb.LanguageServer.CancelRequestResponse> getCancelRequestMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CancelRequest",
      requestType = ksoft.language_server_pb.LanguageServer.CancelRequestRequest.class,
      responseType = ksoft.language_server_pb.LanguageServer.CancelRequestResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.CancelRequestRequest,
      ksoft.language_server_pb.LanguageServer.CancelRequestResponse> getCancelRequestMethod() {
    io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.CancelRequestRequest, ksoft.language_server_pb.LanguageServer.CancelRequestResponse> getCancelRequestMethod;
    if ((getCancelRequestMethod = LanguageServerServiceGrpc.getCancelRequestMethod) == null) {
      synchronized (LanguageServerServiceGrpc.class) {
        if ((getCancelRequestMethod = LanguageServerServiceGrpc.getCancelRequestMethod) == null) {
          LanguageServerServiceGrpc.getCancelRequestMethod = getCancelRequestMethod =
              io.grpc.MethodDescriptor.<ksoft.language_server_pb.LanguageServer.CancelRequestRequest, ksoft.language_server_pb.LanguageServer.CancelRequestResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CancelRequest"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.language_server_pb.LanguageServer.CancelRequestRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.language_server_pb.LanguageServer.CancelRequestResponse.getDefaultInstance()))
              .setSchemaDescriptor(new LanguageServerServiceMethodDescriptorSupplier("CancelRequest"))
              .build();
        }
      }
    }
    return getCancelRequestMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.ExitRequest,
      ksoft.language_server_pb.LanguageServer.ExitResponse> getExitMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Exit",
      requestType = ksoft.language_server_pb.LanguageServer.ExitRequest.class,
      responseType = ksoft.language_server_pb.LanguageServer.ExitResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.ExitRequest,
      ksoft.language_server_pb.LanguageServer.ExitResponse> getExitMethod() {
    io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.ExitRequest, ksoft.language_server_pb.LanguageServer.ExitResponse> getExitMethod;
    if ((getExitMethod = LanguageServerServiceGrpc.getExitMethod) == null) {
      synchronized (LanguageServerServiceGrpc.class) {
        if ((getExitMethod = LanguageServerServiceGrpc.getExitMethod) == null) {
          LanguageServerServiceGrpc.getExitMethod = getExitMethod =
              io.grpc.MethodDescriptor.<ksoft.language_server_pb.LanguageServer.ExitRequest, ksoft.language_server_pb.LanguageServer.ExitResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "Exit"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.language_server_pb.LanguageServer.ExitRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.language_server_pb.LanguageServer.ExitResponse.getDefaultInstance()))
              .setSchemaDescriptor(new LanguageServerServiceMethodDescriptorSupplier("Exit"))
              .build();
        }
      }
    }
    return getExitMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.GetProcessesRequest,
      ksoft.language_server_pb.LanguageServer.GetProcessesResponse> getGetProcessesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetProcesses",
      requestType = ksoft.language_server_pb.LanguageServer.GetProcessesRequest.class,
      responseType = ksoft.language_server_pb.LanguageServer.GetProcessesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.GetProcessesRequest,
      ksoft.language_server_pb.LanguageServer.GetProcessesResponse> getGetProcessesMethod() {
    io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.GetProcessesRequest, ksoft.language_server_pb.LanguageServer.GetProcessesResponse> getGetProcessesMethod;
    if ((getGetProcessesMethod = LanguageServerServiceGrpc.getGetProcessesMethod) == null) {
      synchronized (LanguageServerServiceGrpc.class) {
        if ((getGetProcessesMethod = LanguageServerServiceGrpc.getGetProcessesMethod) == null) {
          LanguageServerServiceGrpc.getGetProcessesMethod = getGetProcessesMethod =
              io.grpc.MethodDescriptor.<ksoft.language_server_pb.LanguageServer.GetProcessesRequest, ksoft.language_server_pb.LanguageServer.GetProcessesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetProcesses"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.language_server_pb.LanguageServer.GetProcessesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.language_server_pb.LanguageServer.GetProcessesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new LanguageServerServiceMethodDescriptorSupplier("GetProcesses"))
              .build();
        }
      }
    }
    return getGetProcessesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.GetChatMessageRequest,
      ksoft.language_server_pb.LanguageServer.GetChatMessageResponse> getGetChatMessageMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetChatMessage",
      requestType = ksoft.language_server_pb.LanguageServer.GetChatMessageRequest.class,
      responseType = ksoft.language_server_pb.LanguageServer.GetChatMessageResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.GetChatMessageRequest,
      ksoft.language_server_pb.LanguageServer.GetChatMessageResponse> getGetChatMessageMethod() {
    io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.GetChatMessageRequest, ksoft.language_server_pb.LanguageServer.GetChatMessageResponse> getGetChatMessageMethod;
    if ((getGetChatMessageMethod = LanguageServerServiceGrpc.getGetChatMessageMethod) == null) {
      synchronized (LanguageServerServiceGrpc.class) {
        if ((getGetChatMessageMethod = LanguageServerServiceGrpc.getGetChatMessageMethod) == null) {
          LanguageServerServiceGrpc.getGetChatMessageMethod = getGetChatMessageMethod =
              io.grpc.MethodDescriptor.<ksoft.language_server_pb.LanguageServer.GetChatMessageRequest, ksoft.language_server_pb.LanguageServer.GetChatMessageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetChatMessage"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.language_server_pb.LanguageServer.GetChatMessageRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.language_server_pb.LanguageServer.GetChatMessageResponse.getDefaultInstance()))
              .setSchemaDescriptor(new LanguageServerServiceMethodDescriptorSupplier("GetChatMessage"))
              .build();
        }
      }
    }
    return getGetChatMessageMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.RecordChatFeedbackRequest,
      ksoft.language_server_pb.LanguageServer.RecordChatFeedbackResponse> getRecordChatFeedbackMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RecordChatFeedback",
      requestType = ksoft.language_server_pb.LanguageServer.RecordChatFeedbackRequest.class,
      responseType = ksoft.language_server_pb.LanguageServer.RecordChatFeedbackResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.RecordChatFeedbackRequest,
      ksoft.language_server_pb.LanguageServer.RecordChatFeedbackResponse> getRecordChatFeedbackMethod() {
    io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.RecordChatFeedbackRequest, ksoft.language_server_pb.LanguageServer.RecordChatFeedbackResponse> getRecordChatFeedbackMethod;
    if ((getRecordChatFeedbackMethod = LanguageServerServiceGrpc.getRecordChatFeedbackMethod) == null) {
      synchronized (LanguageServerServiceGrpc.class) {
        if ((getRecordChatFeedbackMethod = LanguageServerServiceGrpc.getRecordChatFeedbackMethod) == null) {
          LanguageServerServiceGrpc.getRecordChatFeedbackMethod = getRecordChatFeedbackMethod =
              io.grpc.MethodDescriptor.<ksoft.language_server_pb.LanguageServer.RecordChatFeedbackRequest, ksoft.language_server_pb.LanguageServer.RecordChatFeedbackResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RecordChatFeedback"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.language_server_pb.LanguageServer.RecordChatFeedbackRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.language_server_pb.LanguageServer.RecordChatFeedbackResponse.getDefaultInstance()))
              .setSchemaDescriptor(new LanguageServerServiceMethodDescriptorSupplier("RecordChatFeedback"))
              .build();
        }
      }
    }
    return getRecordChatFeedbackMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.RecordChatPanelSessionRequest,
      ksoft.language_server_pb.LanguageServer.RecordChatPanelSessionResponse> getRecordChatPanelSessionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RecordChatPanelSession",
      requestType = ksoft.language_server_pb.LanguageServer.RecordChatPanelSessionRequest.class,
      responseType = ksoft.language_server_pb.LanguageServer.RecordChatPanelSessionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.RecordChatPanelSessionRequest,
      ksoft.language_server_pb.LanguageServer.RecordChatPanelSessionResponse> getRecordChatPanelSessionMethod() {
    io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.RecordChatPanelSessionRequest, ksoft.language_server_pb.LanguageServer.RecordChatPanelSessionResponse> getRecordChatPanelSessionMethod;
    if ((getRecordChatPanelSessionMethod = LanguageServerServiceGrpc.getRecordChatPanelSessionMethod) == null) {
      synchronized (LanguageServerServiceGrpc.class) {
        if ((getRecordChatPanelSessionMethod = LanguageServerServiceGrpc.getRecordChatPanelSessionMethod) == null) {
          LanguageServerServiceGrpc.getRecordChatPanelSessionMethod = getRecordChatPanelSessionMethod =
              io.grpc.MethodDescriptor.<ksoft.language_server_pb.LanguageServer.RecordChatPanelSessionRequest, ksoft.language_server_pb.LanguageServer.RecordChatPanelSessionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RecordChatPanelSession"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.language_server_pb.LanguageServer.RecordChatPanelSessionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.language_server_pb.LanguageServer.RecordChatPanelSessionResponse.getDefaultInstance()))
              .setSchemaDescriptor(new LanguageServerServiceMethodDescriptorSupplier("RecordChatPanelSession"))
              .build();
        }
      }
    }
    return getRecordChatPanelSessionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.ClusteredSearchRequest,
      ksoft.language_server_pb.LanguageServer.ClusteredSearchResponse> getClusteredSearchMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ClusteredSearch",
      requestType = ksoft.language_server_pb.LanguageServer.ClusteredSearchRequest.class,
      responseType = ksoft.language_server_pb.LanguageServer.ClusteredSearchResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.ClusteredSearchRequest,
      ksoft.language_server_pb.LanguageServer.ClusteredSearchResponse> getClusteredSearchMethod() {
    io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.ClusteredSearchRequest, ksoft.language_server_pb.LanguageServer.ClusteredSearchResponse> getClusteredSearchMethod;
    if ((getClusteredSearchMethod = LanguageServerServiceGrpc.getClusteredSearchMethod) == null) {
      synchronized (LanguageServerServiceGrpc.class) {
        if ((getClusteredSearchMethod = LanguageServerServiceGrpc.getClusteredSearchMethod) == null) {
          LanguageServerServiceGrpc.getClusteredSearchMethod = getClusteredSearchMethod =
              io.grpc.MethodDescriptor.<ksoft.language_server_pb.LanguageServer.ClusteredSearchRequest, ksoft.language_server_pb.LanguageServer.ClusteredSearchResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ClusteredSearch"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.language_server_pb.LanguageServer.ClusteredSearchRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.language_server_pb.LanguageServer.ClusteredSearchResponse.getDefaultInstance()))
              .setSchemaDescriptor(new LanguageServerServiceMethodDescriptorSupplier("ClusteredSearch"))
              .build();
        }
      }
    }
    return getClusteredSearchMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.ExactSearchRequest,
      ksoft.language_server_pb.LanguageServer.ExactSearchResponse> getExactSearchMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ExactSearch",
      requestType = ksoft.language_server_pb.LanguageServer.ExactSearchRequest.class,
      responseType = ksoft.language_server_pb.LanguageServer.ExactSearchResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.ExactSearchRequest,
      ksoft.language_server_pb.LanguageServer.ExactSearchResponse> getExactSearchMethod() {
    io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.ExactSearchRequest, ksoft.language_server_pb.LanguageServer.ExactSearchResponse> getExactSearchMethod;
    if ((getExactSearchMethod = LanguageServerServiceGrpc.getExactSearchMethod) == null) {
      synchronized (LanguageServerServiceGrpc.class) {
        if ((getExactSearchMethod = LanguageServerServiceGrpc.getExactSearchMethod) == null) {
          LanguageServerServiceGrpc.getExactSearchMethod = getExactSearchMethod =
              io.grpc.MethodDescriptor.<ksoft.language_server_pb.LanguageServer.ExactSearchRequest, ksoft.language_server_pb.LanguageServer.ExactSearchResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ExactSearch"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.language_server_pb.LanguageServer.ExactSearchRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.language_server_pb.LanguageServer.ExactSearchResponse.getDefaultInstance()))
              .setSchemaDescriptor(new LanguageServerServiceMethodDescriptorSupplier("ExactSearch"))
              .build();
        }
      }
    }
    return getExactSearchMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.AddTrackedWorkspaceRequest,
      ksoft.language_server_pb.LanguageServer.AddTrackedWorkspaceResponse> getAddTrackedWorkspaceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AddTrackedWorkspace",
      requestType = ksoft.language_server_pb.LanguageServer.AddTrackedWorkspaceRequest.class,
      responseType = ksoft.language_server_pb.LanguageServer.AddTrackedWorkspaceResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.AddTrackedWorkspaceRequest,
      ksoft.language_server_pb.LanguageServer.AddTrackedWorkspaceResponse> getAddTrackedWorkspaceMethod() {
    io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.AddTrackedWorkspaceRequest, ksoft.language_server_pb.LanguageServer.AddTrackedWorkspaceResponse> getAddTrackedWorkspaceMethod;
    if ((getAddTrackedWorkspaceMethod = LanguageServerServiceGrpc.getAddTrackedWorkspaceMethod) == null) {
      synchronized (LanguageServerServiceGrpc.class) {
        if ((getAddTrackedWorkspaceMethod = LanguageServerServiceGrpc.getAddTrackedWorkspaceMethod) == null) {
          LanguageServerServiceGrpc.getAddTrackedWorkspaceMethod = getAddTrackedWorkspaceMethod =
              io.grpc.MethodDescriptor.<ksoft.language_server_pb.LanguageServer.AddTrackedWorkspaceRequest, ksoft.language_server_pb.LanguageServer.AddTrackedWorkspaceResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AddTrackedWorkspace"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.language_server_pb.LanguageServer.AddTrackedWorkspaceRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.language_server_pb.LanguageServer.AddTrackedWorkspaceResponse.getDefaultInstance()))
              .setSchemaDescriptor(new LanguageServerServiceMethodDescriptorSupplier("AddTrackedWorkspace"))
              .build();
        }
      }
    }
    return getAddTrackedWorkspaceMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.RemoveTrackedWorkspaceRequest,
      ksoft.language_server_pb.LanguageServer.RemoveTrackedWorkspaceResponse> getRemoveTrackedWorkspaceMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RemoveTrackedWorkspace",
      requestType = ksoft.language_server_pb.LanguageServer.RemoveTrackedWorkspaceRequest.class,
      responseType = ksoft.language_server_pb.LanguageServer.RemoveTrackedWorkspaceResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.RemoveTrackedWorkspaceRequest,
      ksoft.language_server_pb.LanguageServer.RemoveTrackedWorkspaceResponse> getRemoveTrackedWorkspaceMethod() {
    io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.RemoveTrackedWorkspaceRequest, ksoft.language_server_pb.LanguageServer.RemoveTrackedWorkspaceResponse> getRemoveTrackedWorkspaceMethod;
    if ((getRemoveTrackedWorkspaceMethod = LanguageServerServiceGrpc.getRemoveTrackedWorkspaceMethod) == null) {
      synchronized (LanguageServerServiceGrpc.class) {
        if ((getRemoveTrackedWorkspaceMethod = LanguageServerServiceGrpc.getRemoveTrackedWorkspaceMethod) == null) {
          LanguageServerServiceGrpc.getRemoveTrackedWorkspaceMethod = getRemoveTrackedWorkspaceMethod =
              io.grpc.MethodDescriptor.<ksoft.language_server_pb.LanguageServer.RemoveTrackedWorkspaceRequest, ksoft.language_server_pb.LanguageServer.RemoveTrackedWorkspaceResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RemoveTrackedWorkspace"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.language_server_pb.LanguageServer.RemoveTrackedWorkspaceRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.language_server_pb.LanguageServer.RemoveTrackedWorkspaceResponse.getDefaultInstance()))
              .setSchemaDescriptor(new LanguageServerServiceMethodDescriptorSupplier("RemoveTrackedWorkspace"))
              .build();
        }
      }
    }
    return getRemoveTrackedWorkspaceMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.RefreshContextForIdeActionRequest,
      ksoft.language_server_pb.LanguageServer.RefreshContextForIdeActionResponse> getRefreshContextForIdeActionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RefreshContextForIdeAction",
      requestType = ksoft.language_server_pb.LanguageServer.RefreshContextForIdeActionRequest.class,
      responseType = ksoft.language_server_pb.LanguageServer.RefreshContextForIdeActionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.RefreshContextForIdeActionRequest,
      ksoft.language_server_pb.LanguageServer.RefreshContextForIdeActionResponse> getRefreshContextForIdeActionMethod() {
    io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.RefreshContextForIdeActionRequest, ksoft.language_server_pb.LanguageServer.RefreshContextForIdeActionResponse> getRefreshContextForIdeActionMethod;
    if ((getRefreshContextForIdeActionMethod = LanguageServerServiceGrpc.getRefreshContextForIdeActionMethod) == null) {
      synchronized (LanguageServerServiceGrpc.class) {
        if ((getRefreshContextForIdeActionMethod = LanguageServerServiceGrpc.getRefreshContextForIdeActionMethod) == null) {
          LanguageServerServiceGrpc.getRefreshContextForIdeActionMethod = getRefreshContextForIdeActionMethod =
              io.grpc.MethodDescriptor.<ksoft.language_server_pb.LanguageServer.RefreshContextForIdeActionRequest, ksoft.language_server_pb.LanguageServer.RefreshContextForIdeActionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RefreshContextForIdeAction"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.language_server_pb.LanguageServer.RefreshContextForIdeActionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.language_server_pb.LanguageServer.RefreshContextForIdeActionResponse.getDefaultInstance()))
              .setSchemaDescriptor(new LanguageServerServiceMethodDescriptorSupplier("RefreshContextForIdeAction"))
              .build();
        }
      }
    }
    return getRefreshContextForIdeActionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.GetFunctionsRequest,
      ksoft.language_server_pb.LanguageServer.GetFunctionsResponse> getGetFunctionsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetFunctions",
      requestType = ksoft.language_server_pb.LanguageServer.GetFunctionsRequest.class,
      responseType = ksoft.language_server_pb.LanguageServer.GetFunctionsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.GetFunctionsRequest,
      ksoft.language_server_pb.LanguageServer.GetFunctionsResponse> getGetFunctionsMethod() {
    io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.GetFunctionsRequest, ksoft.language_server_pb.LanguageServer.GetFunctionsResponse> getGetFunctionsMethod;
    if ((getGetFunctionsMethod = LanguageServerServiceGrpc.getGetFunctionsMethod) == null) {
      synchronized (LanguageServerServiceGrpc.class) {
        if ((getGetFunctionsMethod = LanguageServerServiceGrpc.getGetFunctionsMethod) == null) {
          LanguageServerServiceGrpc.getGetFunctionsMethod = getGetFunctionsMethod =
              io.grpc.MethodDescriptor.<ksoft.language_server_pb.LanguageServer.GetFunctionsRequest, ksoft.language_server_pb.LanguageServer.GetFunctionsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetFunctions"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.language_server_pb.LanguageServer.GetFunctionsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.language_server_pb.LanguageServer.GetFunctionsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new LanguageServerServiceMethodDescriptorSupplier("GetFunctions"))
              .build();
        }
      }
    }
    return getGetFunctionsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.GetUserStatusRequest,
      ksoft.language_server_pb.LanguageServer.GetUserStatusResponse> getGetUserStatusMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetUserStatus",
      requestType = ksoft.language_server_pb.LanguageServer.GetUserStatusRequest.class,
      responseType = ksoft.language_server_pb.LanguageServer.GetUserStatusResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.GetUserStatusRequest,
      ksoft.language_server_pb.LanguageServer.GetUserStatusResponse> getGetUserStatusMethod() {
    io.grpc.MethodDescriptor<ksoft.language_server_pb.LanguageServer.GetUserStatusRequest, ksoft.language_server_pb.LanguageServer.GetUserStatusResponse> getGetUserStatusMethod;
    if ((getGetUserStatusMethod = LanguageServerServiceGrpc.getGetUserStatusMethod) == null) {
      synchronized (LanguageServerServiceGrpc.class) {
        if ((getGetUserStatusMethod = LanguageServerServiceGrpc.getGetUserStatusMethod) == null) {
          LanguageServerServiceGrpc.getGetUserStatusMethod = getGetUserStatusMethod =
              io.grpc.MethodDescriptor.<ksoft.language_server_pb.LanguageServer.GetUserStatusRequest, ksoft.language_server_pb.LanguageServer.GetUserStatusResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetUserStatus"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.language_server_pb.LanguageServer.GetUserStatusRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  ksoft.language_server_pb.LanguageServer.GetUserStatusResponse.getDefaultInstance()))
              .setSchemaDescriptor(new LanguageServerServiceMethodDescriptorSupplier("GetUserStatus"))
              .build();
        }
      }
    }
    return getGetUserStatusMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static LanguageServerServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LanguageServerServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LanguageServerServiceStub>() {
        @java.lang.Override
        public LanguageServerServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LanguageServerServiceStub(channel, callOptions);
        }
      };
    return LanguageServerServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static LanguageServerServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LanguageServerServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LanguageServerServiceBlockingStub>() {
        @java.lang.Override
        public LanguageServerServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LanguageServerServiceBlockingStub(channel, callOptions);
        }
      };
    return LanguageServerServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static LanguageServerServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<LanguageServerServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<LanguageServerServiceFutureStub>() {
        @java.lang.Override
        public LanguageServerServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new LanguageServerServiceFutureStub(channel, callOptions);
        }
      };
    return LanguageServerServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class LanguageServerServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getCompletions(ksoft.language_server_pb.LanguageServer.GetCompletionsRequest request,
        io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.GetCompletionsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetCompletionsMethod(), responseObserver);
    }

    /**
     */
    public void acceptCompletion(ksoft.language_server_pb.LanguageServer.AcceptCompletionRequest request,
        io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.AcceptCompletionResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAcceptCompletionMethod(), responseObserver);
    }

    /**
     */
    public void heartbeat(ksoft.language_server_pb.LanguageServer.HeartbeatRequest request,
        io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.HeartbeatResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getHeartbeatMethod(), responseObserver);
    }

    /**
     */
    public void getAuthToken(ksoft.language_server_pb.LanguageServer.GetAuthTokenRequest request,
        io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.GetAuthTokenResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetAuthTokenMethod(), responseObserver);
    }

    /**
     */
    public void recordEvent(ksoft.language_server_pb.LanguageServer.RecordEventRequest request,
        io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.RecordEventResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRecordEventMethod(), responseObserver);
    }

    /**
     */
    public void cancelRequest(ksoft.language_server_pb.LanguageServer.CancelRequestRequest request,
        io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.CancelRequestResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCancelRequestMethod(), responseObserver);
    }

    /**
     */
    public void exit(ksoft.language_server_pb.LanguageServer.ExitRequest request,
        io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.ExitResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getExitMethod(), responseObserver);
    }

    /**
     */
    public void getProcesses(ksoft.language_server_pb.LanguageServer.GetProcessesRequest request,
        io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.GetProcessesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetProcessesMethod(), responseObserver);
    }

    /**
     * <pre>
     * Chat RPCs.
     * </pre>
     */
    public void getChatMessage(ksoft.language_server_pb.LanguageServer.GetChatMessageRequest request,
        io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.GetChatMessageResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetChatMessageMethod(), responseObserver);
    }

    /**
     */
    public void recordChatFeedback(ksoft.language_server_pb.LanguageServer.RecordChatFeedbackRequest request,
        io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.RecordChatFeedbackResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRecordChatFeedbackMethod(), responseObserver);
    }

    /**
     */
    public void recordChatPanelSession(ksoft.language_server_pb.LanguageServer.RecordChatPanelSessionRequest request,
        io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.RecordChatPanelSessionResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRecordChatPanelSessionMethod(), responseObserver);
    }

    /**
     * <pre>
     * Search RPCs.
     * </pre>
     */
    public void clusteredSearch(ksoft.language_server_pb.LanguageServer.ClusteredSearchRequest request,
        io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.ClusteredSearchResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getClusteredSearchMethod(), responseObserver);
    }

    /**
     */
    public void exactSearch(ksoft.language_server_pb.LanguageServer.ExactSearchRequest request,
        io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.ExactSearchResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getExactSearchMethod(), responseObserver);
    }

    /**
     */
    public void addTrackedWorkspace(ksoft.language_server_pb.LanguageServer.AddTrackedWorkspaceRequest request,
        io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.AddTrackedWorkspaceResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAddTrackedWorkspaceMethod(), responseObserver);
    }

    /**
     */
    public void removeTrackedWorkspace(ksoft.language_server_pb.LanguageServer.RemoveTrackedWorkspaceRequest request,
        io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.RemoveTrackedWorkspaceResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRemoveTrackedWorkspaceMethod(), responseObserver);
    }

    /**
     * <pre>
     * Refresh context with editor state on IDE events happening.
     * </pre>
     */
    public void refreshContextForIdeAction(ksoft.language_server_pb.LanguageServer.RefreshContextForIdeActionRequest request,
        io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.RefreshContextForIdeActionResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRefreshContextForIdeActionMethod(), responseObserver);
    }

    /**
     */
    public void getFunctions(ksoft.language_server_pb.LanguageServer.GetFunctionsRequest request,
        io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.GetFunctionsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetFunctionsMethod(), responseObserver);
    }

    /**
     */
    public void getUserStatus(ksoft.language_server_pb.LanguageServer.GetUserStatusRequest request,
        io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.GetUserStatusResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetUserStatusMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetCompletionsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                ksoft.language_server_pb.LanguageServer.GetCompletionsRequest,
                ksoft.language_server_pb.LanguageServer.GetCompletionsResponse>(
                  this, METHODID_GET_COMPLETIONS)))
          .addMethod(
            getAcceptCompletionMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                ksoft.language_server_pb.LanguageServer.AcceptCompletionRequest,
                ksoft.language_server_pb.LanguageServer.AcceptCompletionResponse>(
                  this, METHODID_ACCEPT_COMPLETION)))
          .addMethod(
            getHeartbeatMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                ksoft.language_server_pb.LanguageServer.HeartbeatRequest,
                ksoft.language_server_pb.LanguageServer.HeartbeatResponse>(
                  this, METHODID_HEARTBEAT)))
          .addMethod(
            getGetAuthTokenMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                ksoft.language_server_pb.LanguageServer.GetAuthTokenRequest,
                ksoft.language_server_pb.LanguageServer.GetAuthTokenResponse>(
                  this, METHODID_GET_AUTH_TOKEN)))
          .addMethod(
            getRecordEventMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                ksoft.language_server_pb.LanguageServer.RecordEventRequest,
                ksoft.language_server_pb.LanguageServer.RecordEventResponse>(
                  this, METHODID_RECORD_EVENT)))
          .addMethod(
            getCancelRequestMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                ksoft.language_server_pb.LanguageServer.CancelRequestRequest,
                ksoft.language_server_pb.LanguageServer.CancelRequestResponse>(
                  this, METHODID_CANCEL_REQUEST)))
          .addMethod(
            getExitMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                ksoft.language_server_pb.LanguageServer.ExitRequest,
                ksoft.language_server_pb.LanguageServer.ExitResponse>(
                  this, METHODID_EXIT)))
          .addMethod(
            getGetProcessesMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                ksoft.language_server_pb.LanguageServer.GetProcessesRequest,
                ksoft.language_server_pb.LanguageServer.GetProcessesResponse>(
                  this, METHODID_GET_PROCESSES)))
          .addMethod(
            getGetChatMessageMethod(),
            io.grpc.stub.ServerCalls.asyncServerStreamingCall(
              new MethodHandlers<
                ksoft.language_server_pb.LanguageServer.GetChatMessageRequest,
                ksoft.language_server_pb.LanguageServer.GetChatMessageResponse>(
                  this, METHODID_GET_CHAT_MESSAGE)))
          .addMethod(
            getRecordChatFeedbackMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                ksoft.language_server_pb.LanguageServer.RecordChatFeedbackRequest,
                ksoft.language_server_pb.LanguageServer.RecordChatFeedbackResponse>(
                  this, METHODID_RECORD_CHAT_FEEDBACK)))
          .addMethod(
            getRecordChatPanelSessionMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                ksoft.language_server_pb.LanguageServer.RecordChatPanelSessionRequest,
                ksoft.language_server_pb.LanguageServer.RecordChatPanelSessionResponse>(
                  this, METHODID_RECORD_CHAT_PANEL_SESSION)))
          .addMethod(
            getClusteredSearchMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                ksoft.language_server_pb.LanguageServer.ClusteredSearchRequest,
                ksoft.language_server_pb.LanguageServer.ClusteredSearchResponse>(
                  this, METHODID_CLUSTERED_SEARCH)))
          .addMethod(
            getExactSearchMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                ksoft.language_server_pb.LanguageServer.ExactSearchRequest,
                ksoft.language_server_pb.LanguageServer.ExactSearchResponse>(
                  this, METHODID_EXACT_SEARCH)))
          .addMethod(
            getAddTrackedWorkspaceMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                ksoft.language_server_pb.LanguageServer.AddTrackedWorkspaceRequest,
                ksoft.language_server_pb.LanguageServer.AddTrackedWorkspaceResponse>(
                  this, METHODID_ADD_TRACKED_WORKSPACE)))
          .addMethod(
            getRemoveTrackedWorkspaceMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                ksoft.language_server_pb.LanguageServer.RemoveTrackedWorkspaceRequest,
                ksoft.language_server_pb.LanguageServer.RemoveTrackedWorkspaceResponse>(
                  this, METHODID_REMOVE_TRACKED_WORKSPACE)))
          .addMethod(
            getRefreshContextForIdeActionMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                ksoft.language_server_pb.LanguageServer.RefreshContextForIdeActionRequest,
                ksoft.language_server_pb.LanguageServer.RefreshContextForIdeActionResponse>(
                  this, METHODID_REFRESH_CONTEXT_FOR_IDE_ACTION)))
          .addMethod(
            getGetFunctionsMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                ksoft.language_server_pb.LanguageServer.GetFunctionsRequest,
                ksoft.language_server_pb.LanguageServer.GetFunctionsResponse>(
                  this, METHODID_GET_FUNCTIONS)))
          .addMethod(
            getGetUserStatusMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                ksoft.language_server_pb.LanguageServer.GetUserStatusRequest,
                ksoft.language_server_pb.LanguageServer.GetUserStatusResponse>(
                  this, METHODID_GET_USER_STATUS)))
          .build();
    }
  }

  /**
   */
  public static final class LanguageServerServiceStub extends io.grpc.stub.AbstractAsyncStub<LanguageServerServiceStub> {
    private LanguageServerServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LanguageServerServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LanguageServerServiceStub(channel, callOptions);
    }

    /**
     */
    public void getCompletions(ksoft.language_server_pb.LanguageServer.GetCompletionsRequest request,
        io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.GetCompletionsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetCompletionsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void acceptCompletion(ksoft.language_server_pb.LanguageServer.AcceptCompletionRequest request,
        io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.AcceptCompletionResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAcceptCompletionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void heartbeat(ksoft.language_server_pb.LanguageServer.HeartbeatRequest request,
        io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.HeartbeatResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getHeartbeatMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getAuthToken(ksoft.language_server_pb.LanguageServer.GetAuthTokenRequest request,
        io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.GetAuthTokenResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetAuthTokenMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void recordEvent(ksoft.language_server_pb.LanguageServer.RecordEventRequest request,
        io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.RecordEventResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRecordEventMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void cancelRequest(ksoft.language_server_pb.LanguageServer.CancelRequestRequest request,
        io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.CancelRequestResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCancelRequestMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void exit(ksoft.language_server_pb.LanguageServer.ExitRequest request,
        io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.ExitResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getExitMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getProcesses(ksoft.language_server_pb.LanguageServer.GetProcessesRequest request,
        io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.GetProcessesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetProcessesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Chat RPCs.
     * </pre>
     */
    public void getChatMessage(ksoft.language_server_pb.LanguageServer.GetChatMessageRequest request,
        io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.GetChatMessageResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncServerStreamingCall(
          getChannel().newCall(getGetChatMessageMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void recordChatFeedback(ksoft.language_server_pb.LanguageServer.RecordChatFeedbackRequest request,
        io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.RecordChatFeedbackResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRecordChatFeedbackMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void recordChatPanelSession(ksoft.language_server_pb.LanguageServer.RecordChatPanelSessionRequest request,
        io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.RecordChatPanelSessionResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRecordChatPanelSessionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Search RPCs.
     * </pre>
     */
    public void clusteredSearch(ksoft.language_server_pb.LanguageServer.ClusteredSearchRequest request,
        io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.ClusteredSearchResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getClusteredSearchMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void exactSearch(ksoft.language_server_pb.LanguageServer.ExactSearchRequest request,
        io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.ExactSearchResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getExactSearchMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void addTrackedWorkspace(ksoft.language_server_pb.LanguageServer.AddTrackedWorkspaceRequest request,
        io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.AddTrackedWorkspaceResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAddTrackedWorkspaceMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void removeTrackedWorkspace(ksoft.language_server_pb.LanguageServer.RemoveTrackedWorkspaceRequest request,
        io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.RemoveTrackedWorkspaceResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRemoveTrackedWorkspaceMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     * <pre>
     * Refresh context with editor state on IDE events happening.
     * </pre>
     */
    public void refreshContextForIdeAction(ksoft.language_server_pb.LanguageServer.RefreshContextForIdeActionRequest request,
        io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.RefreshContextForIdeActionResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRefreshContextForIdeActionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getFunctions(ksoft.language_server_pb.LanguageServer.GetFunctionsRequest request,
        io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.GetFunctionsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetFunctionsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getUserStatus(ksoft.language_server_pb.LanguageServer.GetUserStatusRequest request,
        io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.GetUserStatusResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetUserStatusMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class LanguageServerServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<LanguageServerServiceBlockingStub> {
    private LanguageServerServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LanguageServerServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LanguageServerServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public ksoft.language_server_pb.LanguageServer.GetCompletionsResponse getCompletions(ksoft.language_server_pb.LanguageServer.GetCompletionsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetCompletionsMethod(), getCallOptions(), request);
    }

    /**
     */
    public ksoft.language_server_pb.LanguageServer.AcceptCompletionResponse acceptCompletion(ksoft.language_server_pb.LanguageServer.AcceptCompletionRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAcceptCompletionMethod(), getCallOptions(), request);
    }

    /**
     */
    public ksoft.language_server_pb.LanguageServer.HeartbeatResponse heartbeat(ksoft.language_server_pb.LanguageServer.HeartbeatRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getHeartbeatMethod(), getCallOptions(), request);
    }

    /**
     */
    public ksoft.language_server_pb.LanguageServer.GetAuthTokenResponse getAuthToken(ksoft.language_server_pb.LanguageServer.GetAuthTokenRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetAuthTokenMethod(), getCallOptions(), request);
    }

    /**
     */
    public ksoft.language_server_pb.LanguageServer.RecordEventResponse recordEvent(ksoft.language_server_pb.LanguageServer.RecordEventRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRecordEventMethod(), getCallOptions(), request);
    }

    /**
     */
    public ksoft.language_server_pb.LanguageServer.CancelRequestResponse cancelRequest(ksoft.language_server_pb.LanguageServer.CancelRequestRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCancelRequestMethod(), getCallOptions(), request);
    }

    /**
     */
    public ksoft.language_server_pb.LanguageServer.ExitResponse exit(ksoft.language_server_pb.LanguageServer.ExitRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getExitMethod(), getCallOptions(), request);
    }

    /**
     */
    public ksoft.language_server_pb.LanguageServer.GetProcessesResponse getProcesses(ksoft.language_server_pb.LanguageServer.GetProcessesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetProcessesMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Chat RPCs.
     * </pre>
     */
    public java.util.Iterator<ksoft.language_server_pb.LanguageServer.GetChatMessageResponse> getChatMessage(
        ksoft.language_server_pb.LanguageServer.GetChatMessageRequest request) {
      return io.grpc.stub.ClientCalls.blockingServerStreamingCall(
          getChannel(), getGetChatMessageMethod(), getCallOptions(), request);
    }

    /**
     */
    public ksoft.language_server_pb.LanguageServer.RecordChatFeedbackResponse recordChatFeedback(ksoft.language_server_pb.LanguageServer.RecordChatFeedbackRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRecordChatFeedbackMethod(), getCallOptions(), request);
    }

    /**
     */
    public ksoft.language_server_pb.LanguageServer.RecordChatPanelSessionResponse recordChatPanelSession(ksoft.language_server_pb.LanguageServer.RecordChatPanelSessionRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRecordChatPanelSessionMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Search RPCs.
     * </pre>
     */
    public ksoft.language_server_pb.LanguageServer.ClusteredSearchResponse clusteredSearch(ksoft.language_server_pb.LanguageServer.ClusteredSearchRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getClusteredSearchMethod(), getCallOptions(), request);
    }

    /**
     */
    public ksoft.language_server_pb.LanguageServer.ExactSearchResponse exactSearch(ksoft.language_server_pb.LanguageServer.ExactSearchRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getExactSearchMethod(), getCallOptions(), request);
    }

    /**
     */
    public ksoft.language_server_pb.LanguageServer.AddTrackedWorkspaceResponse addTrackedWorkspace(ksoft.language_server_pb.LanguageServer.AddTrackedWorkspaceRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAddTrackedWorkspaceMethod(), getCallOptions(), request);
    }

    /**
     */
    public ksoft.language_server_pb.LanguageServer.RemoveTrackedWorkspaceResponse removeTrackedWorkspace(ksoft.language_server_pb.LanguageServer.RemoveTrackedWorkspaceRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRemoveTrackedWorkspaceMethod(), getCallOptions(), request);
    }

    /**
     * <pre>
     * Refresh context with editor state on IDE events happening.
     * </pre>
     */
    public ksoft.language_server_pb.LanguageServer.RefreshContextForIdeActionResponse refreshContextForIdeAction(ksoft.language_server_pb.LanguageServer.RefreshContextForIdeActionRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRefreshContextForIdeActionMethod(), getCallOptions(), request);
    }

    /**
     */
    public ksoft.language_server_pb.LanguageServer.GetFunctionsResponse getFunctions(ksoft.language_server_pb.LanguageServer.GetFunctionsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetFunctionsMethod(), getCallOptions(), request);
    }

    /**
     */
    public ksoft.language_server_pb.LanguageServer.GetUserStatusResponse getUserStatus(ksoft.language_server_pb.LanguageServer.GetUserStatusRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetUserStatusMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class LanguageServerServiceFutureStub extends io.grpc.stub.AbstractFutureStub<LanguageServerServiceFutureStub> {
    private LanguageServerServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected LanguageServerServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new LanguageServerServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ksoft.language_server_pb.LanguageServer.GetCompletionsResponse> getCompletions(
        ksoft.language_server_pb.LanguageServer.GetCompletionsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetCompletionsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ksoft.language_server_pb.LanguageServer.AcceptCompletionResponse> acceptCompletion(
        ksoft.language_server_pb.LanguageServer.AcceptCompletionRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAcceptCompletionMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ksoft.language_server_pb.LanguageServer.HeartbeatResponse> heartbeat(
        ksoft.language_server_pb.LanguageServer.HeartbeatRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getHeartbeatMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ksoft.language_server_pb.LanguageServer.GetAuthTokenResponse> getAuthToken(
        ksoft.language_server_pb.LanguageServer.GetAuthTokenRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetAuthTokenMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ksoft.language_server_pb.LanguageServer.RecordEventResponse> recordEvent(
        ksoft.language_server_pb.LanguageServer.RecordEventRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRecordEventMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ksoft.language_server_pb.LanguageServer.CancelRequestResponse> cancelRequest(
        ksoft.language_server_pb.LanguageServer.CancelRequestRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCancelRequestMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ksoft.language_server_pb.LanguageServer.ExitResponse> exit(
        ksoft.language_server_pb.LanguageServer.ExitRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getExitMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ksoft.language_server_pb.LanguageServer.GetProcessesResponse> getProcesses(
        ksoft.language_server_pb.LanguageServer.GetProcessesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetProcessesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ksoft.language_server_pb.LanguageServer.RecordChatFeedbackResponse> recordChatFeedback(
        ksoft.language_server_pb.LanguageServer.RecordChatFeedbackRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRecordChatFeedbackMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ksoft.language_server_pb.LanguageServer.RecordChatPanelSessionResponse> recordChatPanelSession(
        ksoft.language_server_pb.LanguageServer.RecordChatPanelSessionRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRecordChatPanelSessionMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Search RPCs.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<ksoft.language_server_pb.LanguageServer.ClusteredSearchResponse> clusteredSearch(
        ksoft.language_server_pb.LanguageServer.ClusteredSearchRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getClusteredSearchMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ksoft.language_server_pb.LanguageServer.ExactSearchResponse> exactSearch(
        ksoft.language_server_pb.LanguageServer.ExactSearchRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getExactSearchMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ksoft.language_server_pb.LanguageServer.AddTrackedWorkspaceResponse> addTrackedWorkspace(
        ksoft.language_server_pb.LanguageServer.AddTrackedWorkspaceRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAddTrackedWorkspaceMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ksoft.language_server_pb.LanguageServer.RemoveTrackedWorkspaceResponse> removeTrackedWorkspace(
        ksoft.language_server_pb.LanguageServer.RemoveTrackedWorkspaceRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRemoveTrackedWorkspaceMethod(), getCallOptions()), request);
    }

    /**
     * <pre>
     * Refresh context with editor state on IDE events happening.
     * </pre>
     */
    public com.google.common.util.concurrent.ListenableFuture<ksoft.language_server_pb.LanguageServer.RefreshContextForIdeActionResponse> refreshContextForIdeAction(
        ksoft.language_server_pb.LanguageServer.RefreshContextForIdeActionRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRefreshContextForIdeActionMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ksoft.language_server_pb.LanguageServer.GetFunctionsResponse> getFunctions(
        ksoft.language_server_pb.LanguageServer.GetFunctionsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetFunctionsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<ksoft.language_server_pb.LanguageServer.GetUserStatusResponse> getUserStatus(
        ksoft.language_server_pb.LanguageServer.GetUserStatusRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetUserStatusMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_COMPLETIONS = 0;
  private static final int METHODID_ACCEPT_COMPLETION = 1;
  private static final int METHODID_HEARTBEAT = 2;
  private static final int METHODID_GET_AUTH_TOKEN = 3;
  private static final int METHODID_RECORD_EVENT = 4;
  private static final int METHODID_CANCEL_REQUEST = 5;
  private static final int METHODID_EXIT = 6;
  private static final int METHODID_GET_PROCESSES = 7;
  private static final int METHODID_GET_CHAT_MESSAGE = 8;
  private static final int METHODID_RECORD_CHAT_FEEDBACK = 9;
  private static final int METHODID_RECORD_CHAT_PANEL_SESSION = 10;
  private static final int METHODID_CLUSTERED_SEARCH = 11;
  private static final int METHODID_EXACT_SEARCH = 12;
  private static final int METHODID_ADD_TRACKED_WORKSPACE = 13;
  private static final int METHODID_REMOVE_TRACKED_WORKSPACE = 14;
  private static final int METHODID_REFRESH_CONTEXT_FOR_IDE_ACTION = 15;
  private static final int METHODID_GET_FUNCTIONS = 16;
  private static final int METHODID_GET_USER_STATUS = 17;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final LanguageServerServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(LanguageServerServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_COMPLETIONS:
          serviceImpl.getCompletions((ksoft.language_server_pb.LanguageServer.GetCompletionsRequest) request,
              (io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.GetCompletionsResponse>) responseObserver);
          break;
        case METHODID_ACCEPT_COMPLETION:
          serviceImpl.acceptCompletion((ksoft.language_server_pb.LanguageServer.AcceptCompletionRequest) request,
              (io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.AcceptCompletionResponse>) responseObserver);
          break;
        case METHODID_HEARTBEAT:
          serviceImpl.heartbeat((ksoft.language_server_pb.LanguageServer.HeartbeatRequest) request,
              (io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.HeartbeatResponse>) responseObserver);
          break;
        case METHODID_GET_AUTH_TOKEN:
          serviceImpl.getAuthToken((ksoft.language_server_pb.LanguageServer.GetAuthTokenRequest) request,
              (io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.GetAuthTokenResponse>) responseObserver);
          break;
        case METHODID_RECORD_EVENT:
          serviceImpl.recordEvent((ksoft.language_server_pb.LanguageServer.RecordEventRequest) request,
              (io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.RecordEventResponse>) responseObserver);
          break;
        case METHODID_CANCEL_REQUEST:
          serviceImpl.cancelRequest((ksoft.language_server_pb.LanguageServer.CancelRequestRequest) request,
              (io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.CancelRequestResponse>) responseObserver);
          break;
        case METHODID_EXIT:
          serviceImpl.exit((ksoft.language_server_pb.LanguageServer.ExitRequest) request,
              (io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.ExitResponse>) responseObserver);
          break;
        case METHODID_GET_PROCESSES:
          serviceImpl.getProcesses((ksoft.language_server_pb.LanguageServer.GetProcessesRequest) request,
              (io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.GetProcessesResponse>) responseObserver);
          break;
        case METHODID_GET_CHAT_MESSAGE:
          serviceImpl.getChatMessage((ksoft.language_server_pb.LanguageServer.GetChatMessageRequest) request,
              (io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.GetChatMessageResponse>) responseObserver);
          break;
        case METHODID_RECORD_CHAT_FEEDBACK:
          serviceImpl.recordChatFeedback((ksoft.language_server_pb.LanguageServer.RecordChatFeedbackRequest) request,
              (io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.RecordChatFeedbackResponse>) responseObserver);
          break;
        case METHODID_RECORD_CHAT_PANEL_SESSION:
          serviceImpl.recordChatPanelSession((ksoft.language_server_pb.LanguageServer.RecordChatPanelSessionRequest) request,
              (io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.RecordChatPanelSessionResponse>) responseObserver);
          break;
        case METHODID_CLUSTERED_SEARCH:
          serviceImpl.clusteredSearch((ksoft.language_server_pb.LanguageServer.ClusteredSearchRequest) request,
              (io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.ClusteredSearchResponse>) responseObserver);
          break;
        case METHODID_EXACT_SEARCH:
          serviceImpl.exactSearch((ksoft.language_server_pb.LanguageServer.ExactSearchRequest) request,
              (io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.ExactSearchResponse>) responseObserver);
          break;
        case METHODID_ADD_TRACKED_WORKSPACE:
          serviceImpl.addTrackedWorkspace((ksoft.language_server_pb.LanguageServer.AddTrackedWorkspaceRequest) request,
              (io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.AddTrackedWorkspaceResponse>) responseObserver);
          break;
        case METHODID_REMOVE_TRACKED_WORKSPACE:
          serviceImpl.removeTrackedWorkspace((ksoft.language_server_pb.LanguageServer.RemoveTrackedWorkspaceRequest) request,
              (io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.RemoveTrackedWorkspaceResponse>) responseObserver);
          break;
        case METHODID_REFRESH_CONTEXT_FOR_IDE_ACTION:
          serviceImpl.refreshContextForIdeAction((ksoft.language_server_pb.LanguageServer.RefreshContextForIdeActionRequest) request,
              (io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.RefreshContextForIdeActionResponse>) responseObserver);
          break;
        case METHODID_GET_FUNCTIONS:
          serviceImpl.getFunctions((ksoft.language_server_pb.LanguageServer.GetFunctionsRequest) request,
              (io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.GetFunctionsResponse>) responseObserver);
          break;
        case METHODID_GET_USER_STATUS:
          serviceImpl.getUserStatus((ksoft.language_server_pb.LanguageServer.GetUserStatusRequest) request,
              (io.grpc.stub.StreamObserver<ksoft.language_server_pb.LanguageServer.GetUserStatusResponse>) responseObserver);
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

  private static abstract class LanguageServerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    LanguageServerServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return ksoft.language_server_pb.LanguageServer.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("LanguageServerService");
    }
  }

  private static final class LanguageServerServiceFileDescriptorSupplier
      extends LanguageServerServiceBaseDescriptorSupplier {
    LanguageServerServiceFileDescriptorSupplier() {}
  }

  private static final class LanguageServerServiceMethodDescriptorSupplier
      extends LanguageServerServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    LanguageServerServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (LanguageServerServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new LanguageServerServiceFileDescriptorSupplier())
              .addMethod(getGetCompletionsMethod())
              .addMethod(getAcceptCompletionMethod())
              .addMethod(getHeartbeatMethod())
              .addMethod(getGetAuthTokenMethod())
              .addMethod(getRecordEventMethod())
              .addMethod(getCancelRequestMethod())
              .addMethod(getExitMethod())
              .addMethod(getGetProcessesMethod())
              .addMethod(getGetChatMessageMethod())
              .addMethod(getRecordChatFeedbackMethod())
              .addMethod(getRecordChatPanelSessionMethod())
              .addMethod(getClusteredSearchMethod())
              .addMethod(getExactSearchMethod())
              .addMethod(getAddTrackedWorkspaceMethod())
              .addMethod(getRemoveTrackedWorkspaceMethod())
              .addMethod(getRefreshContextForIdeActionMethod())
              .addMethod(getGetFunctionsMethod())
              .addMethod(getGetUserStatusMethod())
              .build();
        }
      }
    }
    return result;
  }
}
