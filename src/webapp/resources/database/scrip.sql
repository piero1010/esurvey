CREATE TABLE [dbo].[ESV_USER](
	[USER_ID] [nvarchar](50) NOT NULL,
	[USER_DSGN] [nvarchar](50) NULL,
	[USER_NAME] [nvarchar](50) NULL,
	[USER_EMAIL] [nvarchar](255) NULL,
	[USER_DIV_CODE] [nvarchar](10) NULL,
	[USER_RANK_CODE] [nvarchar](10) NULL,
	[USER_STS_IND] [nchar](1) NULL,
	[LAST_REC_TXN_USER_ID] [nvarchar](50) NULL,
	[LAST_REC_TXN_TYPE_CODE] [nchar](1) NULL,
	[LAST_REC_TXN_DATE] [datetime] NULL,
 CONSTRAINT [PK_ESV_USER] PRIMARY KEY CLUSTERED 
(
	[USER_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[ESV_USER_ROLE](
	[USER_ID] [nvarchar](50) NOT NULL,
	[USER_ROLE_CODE] [nchar](3) NOT NULL,
	[LAST_REC_TXN_USER_ID] [nvarchar](50) NULL,
	[LAST_REC_TXN_TYPE_CODE] [nchar](1) NULL,
	[LAST_REC_TXN_DATE] [datetime] NULL,
 CONSTRAINT [PK_ESV_USER_ROLE] PRIMARY KEY CLUSTERED 
(
	[USER_ID] ASC,
	[USER_ROLE] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

USE [ESURVEY]
GO
INSERT [dbo].[ESV_USER_ROLE] ([USER_ID], [USER_ROLE_CODE], [LAST_REC_TXN_USER_ID], [LAST_REC_TXN_TYPE_CODE], [LAST_REC_TXN_DATE]) VALUES (N'desurvey01', N'ADM', NULL, NULL, NULL)
GO

CREATE TABLE [dbo].[ESV_COOR](
	[USER_ID] [nvarchar](50) NOT NULL,
	[SRVY_GRP_ID] [numeric](10, 0) NOT NULL,
	[LAST_REC_TXN_USER_ID] [nvarchar](50) NULL,
	[LAST_REC_TXN_TYPE_CODE] [nchar](1) NULL,
	[LAST_REC_TXN_DATE] [datetime] NULL,
 CONSTRAINT [PK_ESV_COOR] PRIMARY KEY CLUSTERED 
(
	[USER_ID] ASC,
	[SRVY_GRP_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

CREATE TABLE [dbo].[ESV_SYS_CNFG](
	[SYS_CNFG_ID] [nvarchar](30) NOT NULL,
	[SYS_CNFG_DESC] [nvarchar](50) NULL,
	[SYS_CNFG_VAL] [nvarchar](500) NULL,
	[SYS_CNFG_GRP] [nvarchar](10) NULL,
	[LAST_REC_TXN_USER_ID] [nvarchar](50) NULL,
	[LAST_REC_TXN_TYPE_CODE] [nchar](1) NULL,
	[LAST_REC_TXN_DATE] [datetime] NULL,
 CONSTRAINT [PK_ESV_SYS_CNFG] PRIMARY KEY CLUSTERED 
(
	[SYS_CNFG_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

USE [ESURVEY]
GO
INSERT [dbo].[ESV_SYS_CNFG] ([SYS_CNFG_ID], [SYS_CNFG_DESC], [SYS_CNFG_VAL], [SYS_CNFG_GRP], [LAST_REC_TXN_USER_ID], [LAST_REC_TXN_TYPE_CODE], [LAST_REC_TXN_DATE]) VALUES (N'LDAP_BASE', N'Base', N'o=hd,dc=ccgo,dc=hksarg', N'LDAP', NULL, N'A', CAST(N'2018-01-01T00:00:00.000' AS DateTime))
GO
INSERT [dbo].[ESV_SYS_CNFG] ([SYS_CNFG_ID], [SYS_CNFG_DESC], [SYS_CNFG_VAL], [SYS_CNFG_GRP], [LAST_REC_TXN_USER_ID], [LAST_REC_TXN_TYPE_CODE], [LAST_REC_TXN_DATE]) VALUES (N'LDAP_PORT ', N'Service Port', N'389', N'LDAP', NULL, N'A', CAST(N'2018-01-01T00:00:00.000' AS DateTime))
GO
INSERT [dbo].[ESV_SYS_CNFG] ([SYS_CNFG_ID], [SYS_CNFG_DESC], [SYS_CNFG_VAL], [SYS_CNFG_GRP], [LAST_REC_TXN_USER_ID], [LAST_REC_TXN_TYPE_CODE], [LAST_REC_TXN_DATE]) VALUES (N'LDAP_PRVD_URL', N'Hostname', N'ldap://dpol01:389/', N'LDAP', NULL, N'A', CAST(N'2018-01-01T00:00:00.000' AS DateTime))
GO
INSERT [dbo].[ESV_SYS_CNFG] ([SYS_CNFG_ID], [SYS_CNFG_DESC], [SYS_CNFG_VAL], [SYS_CNFG_GRP], [LAST_REC_TXN_USER_ID], [LAST_REC_TXN_TYPE_CODE], [LAST_REC_TXN_DATE]) VALUES (N'LDAP_SCR_CRDTL', N'Login Password', N'N4ug7tmD', N'LDAP', NULL, N'A', CAST(N'2018-01-01T00:00:00.000' AS DateTime))
GO
INSERT [dbo].[ESV_SYS_CNFG] ([SYS_CNFG_ID], [SYS_CNFG_DESC], [SYS_CNFG_VAL], [SYS_CNFG_GRP], [LAST_REC_TXN_USER_ID], [LAST_REC_TXN_TYPE_CODE], [LAST_REC_TXN_DATE]) VALUES (N'LDAP_SCR_PRPL', N'Login Name', N'uid=dpocrldap,ou=users,o=hd,dc=ccgo,dc=hksarg', N'LDAP', NULL, N'A', CAST(N'2018-01-01T00:00:00.000' AS DateTime))
GO
INSERT [dbo].[ESV_SYS_CNFG] ([SYS_CNFG_ID], [SYS_CNFG_DESC], [SYS_CNFG_VAL], [SYS_CNFG_GRP], [LAST_REC_TXN_USER_ID], [LAST_REC_TXN_TYPE_CODE], [LAST_REC_TXN_DATE]) VALUES (N'SMTP_HSTNM', N'Hostname', NULL, N'SMTP', NULL, N'A', CAST(N'2018-01-01T00:00:00.000' AS DateTime))
GO
INSERT [dbo].[ESV_SYS_CNFG] ([SYS_CNFG_ID], [SYS_CNFG_DESC], [SYS_CNFG_VAL], [SYS_CNFG_GRP], [LAST_REC_TXN_USER_ID], [LAST_REC_TXN_TYPE_CODE], [LAST_REC_TXN_DATE]) VALUES (N'SMTP_LGN_ID ', N'Login Name', NULL, N'SMTP', NULL, N'A', CAST(N'2018-01-01T00:00:00.000' AS DateTime))
GO
INSERT [dbo].[ESV_SYS_CNFG] ([SYS_CNFG_ID], [SYS_CNFG_DESC], [SYS_CNFG_VAL], [SYS_CNFG_GRP], [LAST_REC_TXN_USER_ID], [LAST_REC_TXN_TYPE_CODE], [LAST_REC_TXN_DATE]) VALUES (N'SMTP_LGN_PWD', N'Login Password', NULL, N'SMTP', NULL, N'A', CAST(N'2018-01-01T00:00:00.000' AS DateTime))
GO

CREATE TABLE [dbo].[ESV_SRVY_TMPL](
	[SRVY_TMPL_ID] [numeric](10, 0) IDENTITY(1,1) NOT NULL,
	[SRVY_TMPL_NAME] [nvarchar](50) NULL,
	[SRVY_TMPL_CNFG] [ntext] NULL,
	[SRVY_TMPL_FRZ_IND] [nchar](1) NULL,
	[SRVY_GRP_ID] [numeric](10, 0) NULL,
	[SRVY_USER_ID] [nvarchar](50) NULL,
	[LAST_REC_TXN_USER_ID] [nvarchar](50) NULL,
	[LAST_REC_TXN_TYPE_CODE] [nchar](1) NULL,
	[LAST_REC_TXN_DATE] [datetime] NULL,
 CONSTRAINT [PK_ESV_SRVY_TMPL] PRIMARY KEY CLUSTERED 
(
	[SRVY_TMPL_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO

CREATE TABLE [dbo].[ESV_SRVY_GRP](
	[SRVY_GRP_ID] [numeric](10, 0) IDENTITY(1,1) NOT NULL,
	[SRVY_GRP_NAME] [nvarchar](50) NULL,
	[LAST_REC_TXN_USER_ID] [nvarchar](50) NULL,
	[LAST_REC_TXN_TYPE_CODE] [nchar](1) NULL,
	[LAST_REC_TXN_DATE] [datetime] NULL,
 CONSTRAINT [PK_ESV_SRVY_GRP] PRIMARY KEY CLUSTERED 
(
	[SRVY_GRP_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) 
GO
