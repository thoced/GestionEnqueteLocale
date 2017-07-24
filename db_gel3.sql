-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Client :  127.0.0.1
-- Généré le :  Lun 24 Juillet 2017 à 08:01
-- Version du serveur :  5.7.14
-- Version de PHP :  5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `db_gel`
--

-- --------------------------------------------------------

--
-- Structure de la table `t_annexe`
--

CREATE TABLE `t_annexe` (
  `id` bigint(20) NOT NULL,
  `libelle` varchar(256) NOT NULL,
  `commentaire` longtext,
  `raw` longblob NOT NULL,
  `ref_id_folders` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `t_apostille`
--

CREATE TABLE `t_apostille` (
  `id` bigint(20) NOT NULL,
  `autorite` varchar(256) DEFAULT NULL,
  `libelle` varchar(256) DEFAULT NULL,
  `contenu` text,
  `date_IN` date DEFAULT NULL,
  `date_OUT` date DEFAULT NULL,
  `statut` tinyint(1) NOT NULL DEFAULT '0',
  `ref_id_folders` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `t_document`
--

CREATE TABLE `t_document` (
  `id` bigint(20) NOT NULL,
  `ref_id_type` bigint(20) DEFAULT NULL,
  `titre` varchar(256) DEFAULT NULL,
  `commentaire` text,
  `contenu` longtext,
  `date` date NOT NULL,
  `reference` varchar(64) DEFAULT NULL,
  `ref_id_folders` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `t_faits`
--

CREATE TABLE `t_faits` (
  `id` bigint(20) NOT NULL,
  `ref_id_listfaits` bigint(20) NOT NULL,
  `date_basse` timestamp NOT NULL,
  `date_haute` timestamp NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `t_folders`
--

CREATE TABLE `t_folders` (
  `id` bigint(20) NOT NULL,
  `nom` varchar(64) NOT NULL,
  `commentaire` text NOT NULL,
  `owner` bigint(20) DEFAULT NULL,
  `visible` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `t_group`
--

CREATE TABLE `t_group` (
  `id` bigint(20) NOT NULL,
  `group_name` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `t_group`
--

INSERT INTO `t_group` (`id`, `group_name`) VALUES
(1, 'Criminalite urbaine'),
(2, 'Atteinte aux biens'),
(3, 'Atteinte aux personnes');

-- --------------------------------------------------------

--
-- Structure de la table `t_link_annexe_document`
--

CREATE TABLE `t_link_annexe_document` (
  `ref_id_annexe` bigint(20) NOT NULL,
  `ref_id_document` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `t_link_faits_folders`
--

CREATE TABLE `t_link_faits_folders` (
  `id` bigint(20) NOT NULL,
  `ref_id_faits` bigint(20) NOT NULL,
  `ref_id_folders` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `t_link_group_folders`
--

CREATE TABLE `t_link_group_folders` (
  `id` bigint(20) NOT NULL,
  `ref_id_group` bigint(20) NOT NULL,
  `ref_id_folders` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `t_link_group_users`
--

CREATE TABLE `t_link_group_users` (
  `id` bigint(20) NOT NULL,
  `ref_id_users` bigint(20) NOT NULL,
  `ref_id_group` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `t_listfaits`
--

CREATE TABLE `t_listfaits` (
  `id` bigint(20) NOT NULL,
  `fait` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `t_nicetrack`
--

CREATE TABLE `t_nicetrack` (
  `id` bigint(20) NOT NULL,
  `event_id` bigint(20) NOT NULL,
  `date_start` date DEFAULT NULL,
  `time_start` time DEFAULT NULL,
  `date_end` date DEFAULT NULL,
  `time_end` time DEFAULT NULL,
  `num_caller` varchar(32) NOT NULL,
  `num_called` varchar(32) NOT NULL,
  `categorie` varchar(12) DEFAULT NULL,
  `sens` varchar(8) DEFAULT NULL,
  `content` text,
  `synopsis` text,
  `event_type` varchar(12) DEFAULT NULL,
  `ref_id_folders` bigint(20) NOT NULL,
  `ref_id_numero` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `t_numero`
--

CREATE TABLE `t_numero` (
  `id` bigint(20) NOT NULL,
  `type` varchar(32) DEFAULT NULL,
  `numero` varchar(256) DEFAULT NULL,
  `nationalite` varchar(256) DEFAULT NULL,
  `owner` varchar(256) DEFAULT NULL,
  `ref_id_folders` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `t_personne`
--

CREATE TABLE `t_personne` (
  `id` bigint(20) NOT NULL,
  `nom` varchar(128) DEFAULT NULL,
  `prenom` varchar(128) DEFAULT NULL,
  `adresse` varchar(256) DEFAULT NULL,
  `date_naissance` date DEFAULT NULL,
  `qualite` varchar(16) DEFAULT NULL,
  `ref_id_folders` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `t_todo`
--

CREATE TABLE `t_todo` (
  `id` bigint(20) NOT NULL,
  `libelle` varchar(256) DEFAULT NULL,
  `contenu` text,
  `date_creation` date NOT NULL,
  `date_rappel` date DEFAULT NULL,
  `statut` tinyint(1) NOT NULL,
  `ref_id_folders` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `t_type_document`
--

CREATE TABLE `t_type_document` (
  `id` bigint(20) NOT NULL,
  `type` varchar(64) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `t_type_document`
--

INSERT INTO `t_type_document` (`id`, `type`) VALUES
(1, '(PVI) Procès verbal initial'),
(2, '(PVsub) Procès verbal subséquent'),
(3, '(RIR) Rapport d\'information '),
(4, '(RAR) Rapport administratif'),
(5, 'Divers');

-- --------------------------------------------------------

--
-- Structure de la table `t_users`
--

CREATE TABLE `t_users` (
  `id` bigint(20) NOT NULL,
  `login` varchar(16) NOT NULL,
  `nom` varchar(64) NOT NULL,
  `prenom` varchar(64) NOT NULL,
  `password` varchar(128) NOT NULL,
  `reset` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Index pour les tables exportées
--

--
-- Index pour la table `t_annexe`
--
ALTER TABLE `t_annexe`
  ADD PRIMARY KEY (`id`),
  ADD KEY `ref_id_folders` (`ref_id_folders`);

--
-- Index pour la table `t_apostille`
--
ALTER TABLE `t_apostille`
  ADD PRIMARY KEY (`id`),
  ADD KEY `ref_id_folders` (`ref_id_folders`);

--
-- Index pour la table `t_document`
--
ALTER TABLE `t_document`
  ADD PRIMARY KEY (`id`),
  ADD KEY `ref_id_type` (`ref_id_type`),
  ADD KEY `ref_id_folders` (`ref_id_folders`);
ALTER TABLE `t_document` ADD FULLTEXT KEY `contenu` (`contenu`);

--
-- Index pour la table `t_faits`
--
ALTER TABLE `t_faits`
  ADD PRIMARY KEY (`id`),
  ADD KEY `ref_id_listfaits` (`ref_id_listfaits`),
  ADD KEY `ref_id_listfaits_2` (`ref_id_listfaits`);

--
-- Index pour la table `t_folders`
--
ALTER TABLE `t_folders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `owner` (`owner`),
  ADD KEY `owner_2` (`owner`);

--
-- Index pour la table `t_group`
--
ALTER TABLE `t_group`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `t_link_annexe_document`
--
ALTER TABLE `t_link_annexe_document`
  ADD UNIQUE KEY `couple_unique` (`ref_id_annexe`,`ref_id_document`),
  ADD KEY `ref_id_annexe` (`ref_id_annexe`),
  ADD KEY `ref_id_document` (`ref_id_document`);

--
-- Index pour la table `t_link_faits_folders`
--
ALTER TABLE `t_link_faits_folders`
  ADD UNIQUE KEY `id` (`id`),
  ADD KEY `ref_id_faits` (`ref_id_faits`),
  ADD KEY `ref_id_folders` (`ref_id_folders`);

--
-- Index pour la table `t_link_group_folders`
--
ALTER TABLE `t_link_group_folders`
  ADD UNIQUE KEY `id` (`id`),
  ADD KEY `ref_id_group` (`ref_id_group`),
  ADD KEY `ref_id_folders` (`ref_id_folders`),
  ADD KEY `ref_id_group_2` (`ref_id_group`),
  ADD KEY `ref_id_folders_2` (`ref_id_folders`);

--
-- Index pour la table `t_link_group_users`
--
ALTER TABLE `t_link_group_users`
  ADD UNIQUE KEY `id` (`id`),
  ADD KEY `ref_id_users` (`ref_id_users`),
  ADD KEY `ref_id_group` (`ref_id_group`);

--
-- Index pour la table `t_listfaits`
--
ALTER TABLE `t_listfaits`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `t_nicetrack`
--
ALTER TABLE `t_nicetrack`
  ADD PRIMARY KEY (`id`),
  ADD KEY `ref_id_folders` (`ref_id_folders`),
  ADD KEY `ref_id_numero` (`ref_id_numero`);

--
-- Index pour la table `t_numero`
--
ALTER TABLE `t_numero`
  ADD PRIMARY KEY (`id`),
  ADD KEY `ref_id_folders` (`ref_id_folders`);

--
-- Index pour la table `t_personne`
--
ALTER TABLE `t_personne`
  ADD PRIMARY KEY (`id`),
  ADD KEY `ref_id_folders_2` (`ref_id_folders`),
  ADD KEY `ref_id_folders_3` (`ref_id_folders`),
  ADD KEY `ref_id_folders` (`ref_id_folders`) USING BTREE;

--
-- Index pour la table `t_todo`
--
ALTER TABLE `t_todo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `ref_id_folders` (`ref_id_folders`);

--
-- Index pour la table `t_type_document`
--
ALTER TABLE `t_type_document`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `t_users`
--
ALTER TABLE `t_users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `t_annexe`
--
ALTER TABLE `t_annexe`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `t_apostille`
--
ALTER TABLE `t_apostille`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `t_document`
--
ALTER TABLE `t_document`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `t_faits`
--
ALTER TABLE `t_faits`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `t_folders`
--
ALTER TABLE `t_folders`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `t_group`
--
ALTER TABLE `t_group`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `t_link_faits_folders`
--
ALTER TABLE `t_link_faits_folders`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `t_link_group_folders`
--
ALTER TABLE `t_link_group_folders`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `t_link_group_users`
--
ALTER TABLE `t_link_group_users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `t_listfaits`
--
ALTER TABLE `t_listfaits`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `t_nicetrack`
--
ALTER TABLE `t_nicetrack`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `t_numero`
--
ALTER TABLE `t_numero`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `t_personne`
--
ALTER TABLE `t_personne`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `t_todo`
--
ALTER TABLE `t_todo`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `t_type_document`
--
ALTER TABLE `t_type_document`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `t_users`
--
ALTER TABLE `t_users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `t_annexe`
--
ALTER TABLE `t_annexe`
  ADD CONSTRAINT `t_annexe_ibfk_1` FOREIGN KEY (`ref_id_folders`) REFERENCES `t_folders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `t_apostille`
--
ALTER TABLE `t_apostille`
  ADD CONSTRAINT `t_apostille_ibfk_1` FOREIGN KEY (`ref_id_folders`) REFERENCES `t_folders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `t_document`
--
ALTER TABLE `t_document`
  ADD CONSTRAINT `t_document_ibfk_1` FOREIGN KEY (`ref_id_folders`) REFERENCES `t_folders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `t_document_ibfk_2` FOREIGN KEY (`ref_id_type`) REFERENCES `t_type_document` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Contraintes pour la table `t_faits`
--
ALTER TABLE `t_faits`
  ADD CONSTRAINT `t_faits_ibfk_1` FOREIGN KEY (`ref_id_listfaits`) REFERENCES `t_listfaits` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `t_folders`
--
ALTER TABLE `t_folders`
  ADD CONSTRAINT `t_folders_ibfk_1` FOREIGN KEY (`owner`) REFERENCES `t_users` (`id`) ON DELETE SET NULL ON UPDATE SET NULL;

--
-- Contraintes pour la table `t_link_annexe_document`
--
ALTER TABLE `t_link_annexe_document`
  ADD CONSTRAINT `t_link_annexe_document_ibfk_1` FOREIGN KEY (`ref_id_document`) REFERENCES `t_document` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `t_link_annexe_document_ibfk_2` FOREIGN KEY (`ref_id_annexe`) REFERENCES `t_annexe` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `t_link_faits_folders`
--
ALTER TABLE `t_link_faits_folders`
  ADD CONSTRAINT `t_link_faits_folders_ibfk_1` FOREIGN KEY (`ref_id_folders`) REFERENCES `t_folders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `t_link_faits_folders_ibfk_2` FOREIGN KEY (`ref_id_faits`) REFERENCES `t_faits` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `t_link_group_folders`
--
ALTER TABLE `t_link_group_folders`
  ADD CONSTRAINT `t_link_group_folders_ibfk_1` FOREIGN KEY (`ref_id_folders`) REFERENCES `t_folders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `t_link_group_folders_ibfk_2` FOREIGN KEY (`ref_id_group`) REFERENCES `t_group` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `t_link_group_users`
--
ALTER TABLE `t_link_group_users`
  ADD CONSTRAINT `t_link_group_users_ibfk_1` FOREIGN KEY (`ref_id_users`) REFERENCES `t_users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `t_link_group_users_ibfk_2` FOREIGN KEY (`ref_id_group`) REFERENCES `t_group` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `t_nicetrack`
--
ALTER TABLE `t_nicetrack`
  ADD CONSTRAINT `foreign_key_folders` FOREIGN KEY (`ref_id_folders`) REFERENCES `t_folders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `foreign_key_numero` FOREIGN KEY (`ref_id_numero`) REFERENCES `t_numero` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `t_numero`
--
ALTER TABLE `t_numero`
  ADD CONSTRAINT `t_numero_ibfk_1` FOREIGN KEY (`ref_id_folders`) REFERENCES `t_folders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `t_personne`
--
ALTER TABLE `t_personne`
  ADD CONSTRAINT `t_personne_ibfk_1` FOREIGN KEY (`ref_id_folders`) REFERENCES `t_folders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `t_todo`
--
ALTER TABLE `t_todo`
  ADD CONSTRAINT `ref_id_folders` FOREIGN KEY (`ref_id_folders`) REFERENCES `t_folders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
