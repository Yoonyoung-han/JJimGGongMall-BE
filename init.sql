create schema `JJimGGongMall` default character set utf8;
create user yyhan@'%' identified by '1234';
grant all privileges
    on JJimGGongMall.*
    to yyhan@'%';

-- 사용자 정보를 저장하는 테이블
CREATE TABLE Users (
                       userId BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '사용자 ID (자동 증가)',
                       username VARCHAR(255) NOT NULL COMMENT '사용자 이름',
                       email VARCHAR(255) NOT NULL COMMENT '이메일 주소',
                       password VARCHAR(255) NOT NULL COMMENT '비밀번호',
                       createdAt DATETIME NULL COMMENT '생성 날짜',
                       updatedAt DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '업데이트 날짜 (기본값: 현재 시간)'
);

-- 카테고리 정보를 저장하는 테이블
CREATE TABLE Categories (
                            categoryId BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '카테고리 ID (자동 증가)',
                            parentId BIGINT NOT NULL COMMENT '상위 카테고리 ID',
                            code VARCHAR(255) NULL COMMENT '카테고리 코드',
                            name VARCHAR(255) NOT NULL COMMENT '카테고리 이름',
                            depth INT NOT NULL COMMENT '카테고리 깊이',
                            createdAt DATETIME NULL COMMENT '생성 날짜',
                            updatedAt DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '업데이트 날짜 (기본값: 현재 시간)'
);

-- 제품 정보를 저장하는 테이블
CREATE TABLE Products (
                          productId BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '제품 ID (자동 증가)',
                          categoryId BIGINT NOT NULL COMMENT '카테고리 ID',
                          prodName VARCHAR(255) NOT NULL COMMENT '제품 이름',
                          prodDesc TEXT NULL COMMENT '제품 설명',
                          stock BIGINT NULL COMMENT '재고',
                          price DECIMAL(10, 2) NOT NULL COMMENT '가격',
                          thumbnailUrl VARCHAR(255) NULL COMMENT '썸네일 URL',
                          createdAt DATETIME NULL COMMENT '생성 날짜',
                          updatedAt DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '업데이트 날짜 (기본값: 현재 시간)'
);

-- 옵션 정보를 저장하는 테이블
CREATE TABLE Options (
                         optionId BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '옵션 ID (자동 증가)',
                         optionName VARCHAR(255) NOT NULL COMMENT '옵션 이름',
                         createdAt DATETIME NULL COMMENT '생성 날짜',
                         updatedAt DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '업데이트 날짜 (기본값: 현재 시간)'
);

-- 제품 옵션 정보를 저장하는 테이블
CREATE TABLE ProductOptions (
                                productOptionId BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '제품 옵션 ID (자동 증가)',
                                productId BIGINT NOT NULL COMMENT '제품 ID',
                                optionId BIGINT NOT NULL COMMENT '옵션 ID',
                                prodOptionValue VARCHAR(255) NOT NULL COMMENT '옵션 값',
                                stock INT NOT NULL COMMENT '재고',
                                createdAt DATETIME NULL COMMENT '생성 날짜',
                                updatedAt DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '업데이트 날짜 (기본값: 현재 시간)'
);

-- 제품 이미지 정보를 저장하는 테이블
CREATE TABLE ProductImages (
                               productImageId BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '제품 이미지 ID (자동 증가)',
                               productId BIGINT NOT NULL COMMENT '제품 ID',
                               imageUrl VARCHAR(255) NOT NULL COMMENT '이미지 URL',
                               isThumbnail TINYINT(1) NOT NULL COMMENT '썸네일 여부',
                               isDetailImage TINYINT(1) NOT NULL COMMENT '상세 이미지 여부',
                               isPreviewImage TINYINT(1) NOT NULL COMMENT '미리보기 이미지 여부',
                               orderIndex INT NOT NULL COMMENT '이미지 순서',
                               createdAt DATETIME NULL COMMENT '생성 날짜',
                               updatedAt DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '업데이트 날짜 (기본값: 현재 시간)'
);

-- 주문 정보를 저장하는 테이블
CREATE TABLE Orders (
                        orderId BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '주문 ID (자동 증가)',
                        userId BIGINT NOT NULL COMMENT '사용자 ID',
                        orderNumber VARCHAR(255) NOT NULL COMMENT '주문 번호',
                        totalAmount DECIMAL(10, 2) NOT NULL COMMENT '총 금액',
                        discountAmount DECIMAL(10, 2) NULL COMMENT '할인 금액',
                        paymentAmount DECIMAL(10, 2) NOT NULL COMMENT '결제 금액',
                        orderStatus VARCHAR(50) NOT NULL COMMENT '주문 상태',
                        createdAt DATETIME NULL COMMENT '생성 날짜',
                        updatedAt DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '업데이트 날짜 (기본값: 현재 시간)'
);

-- 배송 주소 정보를 저장하는 테이블
CREATE TABLE ShippingAddresses (
                                   shippingAddressId BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '배송 주소 ID (자동 증가)',
                                   userId BIGINT NOT NULL COMMENT '사용자 ID',
                                   recipientName VARCHAR(255) NOT NULL COMMENT '수령인 이름',
                                   addressLine1 VARCHAR(255) NOT NULL COMMENT '주소 1',
                                   addressLine2 VARCHAR(255) NULL COMMENT '주소 2',
                                   city VARCHAR(100) NOT NULL COMMENT '도시',
                                   state VARCHAR(100) NOT NULL COMMENT '주',
                                   postalCode VARCHAR(20) NOT NULL COMMENT '우편번호',
                                   country VARCHAR(100) NOT NULL COMMENT '국가',
                                   isDefault TINYINT(1) NOT NULL COMMENT '기본 주소 여부',
                                   createdAt DATETIME NULL COMMENT '생성 날짜',
                                   updatedAt DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '업데이트 날짜 (기본값: 현재 시간)'
);

-- 환불 정보를 저장하는 테이블
CREATE TABLE Refunds (
                         refundId BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '환불 ID (자동 증가)',
                         orderId BIGINT NOT NULL COMMENT '주문 ID',
                         amount DECIMAL(10, 2) NOT NULL COMMENT '환불 금액',
                         refundStatus VARCHAR(50) NOT NULL COMMENT '환불 상태',
                         createdAt DATETIME NULL COMMENT '생성 날짜',
                         updatedAt DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '업데이트 날짜 (기본값: 현재 시간)'
);

-- 결제 정보를 저장하는 테이블
CREATE TABLE Payments (
                          paymentId BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '결제 ID (자동 증가)',
                          orderId BIGINT NOT NULL COMMENT '주문 ID',
                          amount DECIMAL(10, 2) NOT NULL COMMENT '결제 금액',
                          paymentStatus VARCHAR(50) NOT NULL COMMENT '결제 상태',
                          installmentMonths INT NULL COMMENT '할부 개월 수',
                          paymentMethod VARCHAR(100) NULL COMMENT '결제 방법',
                          cardIssuer VARCHAR(100) NULL COMMENT '카드 발급사',
                          approvalDate DATETIME NULL COMMENT '승인 날짜',
                          createdAt DATETIME NULL COMMENT '생성 날짜',
                          updatedAt DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '업데이트 날짜 (기본값: 현재 시간)'
);

-- 제품 좋아요 정보를 저장하는 테이블
CREATE TABLE ProductLikes (
                              likeId BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '좋아요 ID (자동 증가)',
                              userId BIGINT NOT NULL COMMENT '사용자 ID',
                              productId BIGINT NOT NULL COMMENT '제품 ID',
                              createdAt DATETIME NULL COMMENT '생성 날짜',
                              updatedAt DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '업데이트 날짜 (기본값: 현재 시간)'
);

-- 리뷰 좋아요 정보를 저장하는 테이블
CREATE TABLE ReviewLikes (
                             likeId BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '좋아요 ID (자동 증가)',
                             reviewId BIGINT NOT NULL COMMENT '리뷰 ID',
                             userId BIGINT NOT NULL COMMENT '사용자 ID',
                             createdAt DATETIME NULL COMMENT '생성 날짜',
                             updatedAt DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '업데이트 날짜 (기본값: 현재 시간)'
);

-- 소셜 계정 정보를 저장하는 테이블
CREATE TABLE SocialAccounts (
                                accountId BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '소셜 계정 ID (자동 증가)',
                                userId BIGINT NOT NULL COMMENT '사용자 ID',
                                provider VARCHAR(50) NOT NULL COMMENT '소셜 제공자',
                                socialId VARCHAR(255) NOT NULL COMMENT '소셜 ID',
                                createdAt DATETIME NULL COMMENT '생성 날짜',
                                updatedAt DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '업데이트 날짜 (기본값: 현재 시간)'
);

-- 이메일 인증 정보를 저장하는 테이블
CREATE TABLE EmailVerifications (
                                    emailVerificationId BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '이메일 인증 ID (자동 증가)',
                                    userId BIGINT NOT NULL COMMENT '사용자 ID',
                                    token VARCHAR(255) NOT NULL COMMENT '토큰',
                                    createdAt DATETIME NULL COMMENT '생성 날짜',
                                    updatedAt DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '업데이트 날짜 (기본값: 현재 시간)'
);

-- PG 결제 정보를 저장하는 테이블
CREATE TABLE PGPayments (
                            pgPaymentId BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'PG 결제 ID (자동 증가)',
                            paymentId BIGINT NOT NULL COMMENT '결제 ID',
                            pgProvider VARCHAR(100) NOT NULL COMMENT 'PG 제공자',
                            pgTransactionId VARCHAR(255) NOT NULL COMMENT 'PG 거래 ID',
                            pgStatus VARCHAR(50) NOT NULL COMMENT 'PG 상태',
                            createdAt DATETIME NULL COMMENT '생성 날짜',
                            updatedAt DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '업데이트 날짜 (기본값: 현재 시간)'
);

-- 리뷰 정보를 저장하는 테이블
CREATE TABLE Reviews (
                         reviewId BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '리뷰 ID (자동 증가)',
                         productId BIGINT NOT NULL COMMENT '제품 ID',
                         userId BIGINT NOT NULL COMMENT '사용자 ID',
                         rating INT NOT NULL COMMENT '평점',
                         reviewComment TEXT NULL COMMENT '리뷰 코멘트',
                         createdAt DATETIME NULL COMMENT '생성 날짜',
                         updatedAt DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '업데이트 날짜 (기본값: 현재 시간)'
);

-- 리뷰 이미지 정보를 저장하는 테이블
CREATE TABLE ReviewImages (
                              reviewImageId BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '리뷰 이미지 ID (자동 증가)',
                              reviewId BIGINT NOT NULL COMMENT '리뷰 ID',
                              imageUrl VARCHAR(255) NOT NULL COMMENT '이미지 URL',
                              imageOrder INT NULL COMMENT '이미지 순서',
                              createdAt DATETIME NULL COMMENT '생성 날짜',
                              updatedAt DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '업데이트 날짜 (기본값: 현재 시간)'
);