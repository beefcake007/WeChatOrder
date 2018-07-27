CREATE TABLE `product_info` (
	`product_id` VARCHAR(32) NOT NULL,
	`product_name` VARCHAR(64) NOT NULL COMMENT '��Ʒ����',
	`product_price` DECIMAL(8,2) NOT NULL COMMENT '����',
	`product_stock` INT NOT NULL COMMENT '���',
	`product_description` VARCHAR(64) COMMENT '����',
	`product_icon` VARCHAR(512) COMMENT 'Сͼ',
	`product_status` TINYINT(3) DEFAULT '0' COMMENT '��Ʒ״̬,0����1�¼�',
	`category_type` INT NOT NULL COMMENT '��Ŀ���',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '�޸�ʱ��',
	PRIMARY KEY (`product_id`)
) COMMENT '��Ʒ��';

CREATE TABLE `product_category` (
	`category_id` INT NOT NULL auto_increment,
	`category_name` VARCHAR(64) NOT NULL COMMENT '��Ŀ����',
	`category_type` INT NOT NULL COMMENT '��Ŀ���',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '�޸�ʱ��',
	PRIMARY KEY (`category_id`),
	UNIQUE KEY `uqe_category_type` (`category_type`)
) COMMENT '��Ŀ��';

CREATE TABLE `order_master` (
	`order_id` VARCHAR(32) NOT NULL,
	`buyer_name` VARCHAR(32) NOT NULL COMMENT '�������',
	`buyer_phone` VARCHAR(32) NOT NULL COMMENT '��ҵ绰',
	`buyer_address` VARCHAR(128) NOT NULL COMMENT '��ҵ�ַ',
	`buyer_openid` VARCHAR(64) NOT NULL COMMENT '���΢��openid',
	`order_amount` DECIMAL(8,2) NOT NULL COMMENT '�����ܽ��',
	`order_status` TINYINT(3) NOT NULL DEFAULT '0' COMMENT '����״̬��Ĭ��0���µ�',
	`pay_status` TINYINT(3) NOT NULL DEFAULT '0' COMMENT '֧��״̬��Ĭ��0δ֧��',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '�޸�ʱ��',
	PRIMARY KEY (`order_id`),
	KEY `idx_buyer_openid` (`buyer_openid`)
) COMMENT '������';

CREATE TABLE `order_detail` (
	`detail_id` VARCHAR(32) NOT NULL,
	`order_id` VARCHAR(32) NOT NULL,
	`product_id` VARCHAR(32) NOT NULL,
	`product_name` VARCHAR(64) NOT NULL COMMENT '��Ʒ����',
	`product_price` DECIMAL(8,2) NOT NULL COMMENT '��Ʒ�۸�',
	`product_quantity` INT NOT NULL COMMENT '��Ʒ����',
	`product_icon` VARCHAR(512) COMMENT '��ƷСͼ',
	`create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
	`update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '�޸�ʱ��',
	PRIMARY KEY (`detail_id`),
	KEY `idx_order_id` (`order_id`)
) COMMENT '���������';

