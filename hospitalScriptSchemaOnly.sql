USE [master]
GO
/****** Object:  Database [Hospital]    Script Date: 11/06/2023 22:11:59 ******/
CREATE DATABASE [Hospital]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'Hospital', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\Hospital.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'Hospital_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.SQLEXPRESS\MSSQL\DATA\Hospital_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [Hospital] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [Hospital].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [Hospital] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [Hospital] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [Hospital] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [Hospital] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [Hospital] SET ARITHABORT OFF 
GO
ALTER DATABASE [Hospital] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [Hospital] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [Hospital] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [Hospital] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [Hospital] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [Hospital] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [Hospital] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [Hospital] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [Hospital] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [Hospital] SET  DISABLE_BROKER 
GO
ALTER DATABASE [Hospital] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [Hospital] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [Hospital] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [Hospital] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [Hospital] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [Hospital] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [Hospital] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [Hospital] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [Hospital] SET  MULTI_USER 
GO
ALTER DATABASE [Hospital] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [Hospital] SET DB_CHAINING OFF 
GO
ALTER DATABASE [Hospital] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [Hospital] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [Hospital] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [Hospital] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [Hospital] SET QUERY_STORE = OFF
GO
USE [Hospital]
GO
/****** Object:  Table [dbo].[appointments]    Script Date: 11/06/2023 22:12:00 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[appointments](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[date] [datetime2](6) NULL,
	[doctor_id] [int] NULL,
	[patient_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[directors]    Script Date: 11/06/2023 22:12:00 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[directors](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[person_info_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[doctors]    Script Date: 11/06/2023 22:12:00 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[doctors](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[qualification] [varchar](255) NULL,
	[person_info_id] [int] NULL,
	[ward_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[patients]    Script Date: 11/06/2023 22:12:00 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[patients](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[disease] [varchar](255) NULL,
	[treatment] [varchar](255) NULL,
	[doctor_id] [int] NULL,
	[person_info_id] [int] NULL,
	[ward_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[person_info]    Script Date: 11/06/2023 22:12:00 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[person_info](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[age] [int] NULL,
	[first_name] [varchar](255) NULL,
	[last_name] [varchar](255) NULL,
	[number] [bigint] NULL,
	[user_entity_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[roles]    Script Date: 11/06/2023 22:12:00 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[roles](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[type] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[rooms]    Script Date: 11/06/2023 22:12:00 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[rooms](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[type] [smallint] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[staff]    Script Date: 11/06/2023 22:12:00 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[staff](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[type] [varchar](255) NULL,
	[person_info_id] [int] NULL,
	[ward_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[treatments]    Script Date: 11/06/2023 22:12:00 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[treatments](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[end_date] [datetime2](6) NULL,
	[price_per_day] [float] NULL,
	[start_date] [datetime2](6) NULL,
	[total_price] [float] NULL,
	[patient_id] [int] NULL,
	[room_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[users]    Script Date: 11/06/2023 22:12:00 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[users](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[password] [varchar](255) NULL,
	[username] [varchar](255) NULL,
	[role_id] [int] NULL,
	[email] [varchar](255) NULL,
	[wanted_role] [varbinary](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[wards]    Script Date: 11/06/2023 22:12:00 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[wards](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](255) NULL,
	[head_doctor] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[appointments]  WITH CHECK ADD  CONSTRAINT [FK8exap5wmg8kmb1g1rx3by21yt] FOREIGN KEY([patient_id])
REFERENCES [dbo].[patients] ([id])
GO
ALTER TABLE [dbo].[appointments] CHECK CONSTRAINT [FK8exap5wmg8kmb1g1rx3by21yt]
GO
ALTER TABLE [dbo].[appointments]  WITH CHECK ADD  CONSTRAINT [FKmujeo4tymoo98cmf7uj3vsv76] FOREIGN KEY([doctor_id])
REFERENCES [dbo].[doctors] ([id])
GO
ALTER TABLE [dbo].[appointments] CHECK CONSTRAINT [FKmujeo4tymoo98cmf7uj3vsv76]
GO
ALTER TABLE [dbo].[directors]  WITH CHECK ADD  CONSTRAINT [FKcjayymg1kngoco41g154cprex] FOREIGN KEY([person_info_id])
REFERENCES [dbo].[person_info] ([id])
GO
ALTER TABLE [dbo].[directors] CHECK CONSTRAINT [FKcjayymg1kngoco41g154cprex]
GO
ALTER TABLE [dbo].[doctors]  WITH CHECK ADD  CONSTRAINT [FKfiql1do20npjwtjeucp3v1gr3] FOREIGN KEY([ward_id])
REFERENCES [dbo].[wards] ([id])
GO
ALTER TABLE [dbo].[doctors] CHECK CONSTRAINT [FKfiql1do20npjwtjeucp3v1gr3]
GO
ALTER TABLE [dbo].[doctors]  WITH CHECK ADD  CONSTRAINT [FKsn6kxjq3ctvcvxujp970jw37g] FOREIGN KEY([person_info_id])
REFERENCES [dbo].[person_info] ([id])
GO
ALTER TABLE [dbo].[doctors] CHECK CONSTRAINT [FKsn6kxjq3ctvcvxujp970jw37g]
GO
ALTER TABLE [dbo].[patients]  WITH CHECK ADD  CONSTRAINT [FK4uep0g7kcu02sl8k9x2j5kesx] FOREIGN KEY([ward_id])
REFERENCES [dbo].[wards] ([id])
GO
ALTER TABLE [dbo].[patients] CHECK CONSTRAINT [FK4uep0g7kcu02sl8k9x2j5kesx]
GO
ALTER TABLE [dbo].[patients]  WITH CHECK ADD  CONSTRAINT [FKmg27dqpsyv7sr440hnfv6k2vn] FOREIGN KEY([person_info_id])
REFERENCES [dbo].[person_info] ([id])
GO
ALTER TABLE [dbo].[patients] CHECK CONSTRAINT [FKmg27dqpsyv7sr440hnfv6k2vn]
GO
ALTER TABLE [dbo].[patients]  WITH CHECK ADD  CONSTRAINT [FKperqpk72jxd90l8yq7qf5fsx0] FOREIGN KEY([doctor_id])
REFERENCES [dbo].[doctors] ([id])
GO
ALTER TABLE [dbo].[patients] CHECK CONSTRAINT [FKperqpk72jxd90l8yq7qf5fsx0]
GO
ALTER TABLE [dbo].[person_info]  WITH CHECK ADD  CONSTRAINT [FKr5ex510wegso9qso98to0r6sf] FOREIGN KEY([user_entity_id])
REFERENCES [dbo].[users] ([id])
GO
ALTER TABLE [dbo].[person_info] CHECK CONSTRAINT [FKr5ex510wegso9qso98to0r6sf]
GO
ALTER TABLE [dbo].[staff]  WITH CHECK ADD  CONSTRAINT [FK614di3ywfmauxrqfmub89ws9g] FOREIGN KEY([person_info_id])
REFERENCES [dbo].[person_info] ([id])
GO
ALTER TABLE [dbo].[staff] CHECK CONSTRAINT [FK614di3ywfmauxrqfmub89ws9g]
GO
ALTER TABLE [dbo].[staff]  WITH CHECK ADD  CONSTRAINT [FKb60n9mcm84qkuy6sqdqj6p3s0] FOREIGN KEY([ward_id])
REFERENCES [dbo].[wards] ([id])
GO
ALTER TABLE [dbo].[staff] CHECK CONSTRAINT [FKb60n9mcm84qkuy6sqdqj6p3s0]
GO
ALTER TABLE [dbo].[treatments]  WITH CHECK ADD  CONSTRAINT [FK8djm31y5jedj2s88odydl1mmw] FOREIGN KEY([patient_id])
REFERENCES [dbo].[patients] ([id])
GO
ALTER TABLE [dbo].[treatments] CHECK CONSTRAINT [FK8djm31y5jedj2s88odydl1mmw]
GO
ALTER TABLE [dbo].[treatments]  WITH CHECK ADD  CONSTRAINT [FKt2o8f8m0yr0omqkjflhbruqbt] FOREIGN KEY([room_id])
REFERENCES [dbo].[rooms] ([id])
GO
ALTER TABLE [dbo].[treatments] CHECK CONSTRAINT [FKt2o8f8m0yr0omqkjflhbruqbt]
GO
ALTER TABLE [dbo].[users]  WITH CHECK ADD  CONSTRAINT [FKp56c1712k691lhsyewcssf40f] FOREIGN KEY([role_id])
REFERENCES [dbo].[roles] ([id])
GO
ALTER TABLE [dbo].[users] CHECK CONSTRAINT [FKp56c1712k691lhsyewcssf40f]
GO
ALTER TABLE [dbo].[wards]  WITH CHECK ADD  CONSTRAINT [FKkacf5j438rjcpb9ydya53tu3y] FOREIGN KEY([head_doctor])
REFERENCES [dbo].[doctors] ([id])
GO
ALTER TABLE [dbo].[wards] CHECK CONSTRAINT [FKkacf5j438rjcpb9ydya53tu3y]
GO
ALTER TABLE [dbo].[person_info]  WITH CHECK ADD CHECK  (([number]>=(0)))
GO
ALTER TABLE [dbo].[person_info]  WITH CHECK ADD CHECK  (([age]<=(100) AND [age]>=(18)))
GO
USE [master]
GO
ALTER DATABASE [Hospital] SET  READ_WRITE 
GO
